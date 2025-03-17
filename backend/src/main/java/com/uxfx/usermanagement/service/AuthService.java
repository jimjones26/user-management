package com.uxfx.usermanagement.service;

import com.uxfx.usermanagement.dto.*;
import com.uxfx.usermanagement.model.*;
import com.uxfx.usermanagement.model.Status;
import com.uxfx.usermanagement.repository.*;
import com.uxfx.usermanagement.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final RevokedTokenRepository revokedTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final TOTPService totpService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       EmailVerificationTokenRepository emailVerificationTokenRepository,
                       RevokedTokenRepository revokedTokenRepository,
                       PasswordResetTokenRepository passwordResetTokenRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       EmailService emailService,
                       TOTPService totpService) {
        this.userRepository = userRepository;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.revokedTokenRepository = revokedTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
        this.totpService = totpService;
    }

    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setStatus(Status.INACTIVE);
        user.setEmailVerified(false);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationToken.setCreatedAt(new Date());
        verificationToken.setExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // 24 hours
        verificationToken.setUsed(false);
        emailVerificationTokenRepository.save(verificationToken);

        String verificationLink = "http://localhost:8080/email-verification/verify?token=" + token;
        emailService.sendVerificationEmail(user.getEmail(), verificationLink);
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }
        if (!user.isEmailVerified()) {
            throw new RuntimeException("Email not verified");
        }
        if (user.getMfa() != null && user.getMfa().isVerified()) {
            String tempToken = jwtTokenProvider.generateTempToken(user);
            return new LoginResponse(true, tempToken, null);
        } else {
            String accessToken = jwtTokenProvider.generateAccessToken(user);
            String refreshToken = jwtTokenProvider.generateRefreshToken(user);
            return new LoginResponse(false, accessToken, refreshToken);
        }
    }

    public JwtResponse verifyMFA(MFAVerifyRequest request) {
        Claims claims = jwtTokenProvider.validateTempToken(request.getToken());
        if (claims == null) {
            throw new RuntimeException("Invalid temporary token");
        }
        Long userId = claims.get("userId", Long.class);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        MFA mfa = user.getMfa();
        if (mfa == null || !mfa.isVerified()) {
            throw new RuntimeException("MFA not set up");
        }
        boolean isValid = false;
        if (mfa.getMethod() == MFAMethod.TOTP) {
            isValid = totpService.validateTOTP(mfa.getSecret(), request.getCode());
        } else if (mfa.getMethod() == MFAMethod.SMS) {
            // SMS verification logic would go here (assumed implemented elsewhere)
            throw new RuntimeException("SMS MFA not implemented");
        }
        if (!isValid) {
            throw new RuntimeException("Invalid MFA code");
        }
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);
        return new JwtResponse(accessToken, refreshToken);
    }

    public void revokeToken(String token) {
        if (revokedTokenRepository.existsByToken(token)) {
            throw new RuntimeException("Token already revoked");
        }
        Claims claims = jwtTokenProvider.getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        RevokedToken revokedToken = new RevokedToken();
        revokedToken.setToken(token);
        revokedToken.setRevokedAt(new Date());
        revokedToken.setExpiresAt(expiration);
        revokedTokenRepository.save(revokedToken);
    }

    public String refreshAccessToken(String refreshToken) {
        Claims claims = jwtTokenProvider.validateRefreshToken(refreshToken);
        if (claims == null) {
            throw new RuntimeException("Invalid refresh token");
        }
        if (revokedTokenRepository.existsByToken(refreshToken)) {
            throw new RuntimeException("Refresh token has been revoked");
        }
        Long userId = claims.get("userId", Long.class);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return jwtTokenProvider.generateAccessToken(user);
    }

    public void requestPasswordReset(String identifier) {
         User userOpt = userRepository.findByUsername(identifier);
        if (!userOpt.isPresent()) {
            userOpt = userRepository.findByEmail(identifier);
        }
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getStatus() == Status.ACTIVE) {
                passwordResetTokenRepository.deleteByUserAndIsUsedFalse(user);
                String token = UUID.randomUUID().toString();
                PasswordResetToken resetToken = new PasswordResetToken(user, token);
                passwordResetTokenRepository.save(resetToken);
                emailService.sendPasswordResetEmail(user.getEmail(), token);
            }
        }
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (resetToken.isUsed()) {
            throw new RuntimeException("Token already used");
        }
        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }
        User user = resetToken.getUser();
        if (user.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Cannot reset password for this account");
        }
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPasswordHash(hashedPassword);
        userRepository.save(user);
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
    }
}

package com.uxfx.usermanagement.service;

import com.uxfx.usermanagement.dto.MFASetupResponse;
import com.uxfx.usermanagement.exception.ResourceNotFoundException;
import com.uxfx.usermanagement.model.BackupCode;
import com.uxfx.usermanagement.model.MFA;
import com.uxfx.usermanagement.model.MFAMethod;
import com.uxfx.usermanagement.model.User;
import com.uxfx.usermanagement.repository.BackupCodeRepository;
import com.uxfx.usermanagement.repository.MFARepository;
import com.uxfx.usermanagement.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MFAService {
    private final UserRepository userRepository;
    private final MFARepository mfaRepository;
    private final BackupCodeRepository backupCodeRepository;
    private final TOTPService totpService;
    
    public MFAService(UserRepository userRepository,
                     MFARepository mfaRepository,
                     BackupCodeRepository backupCodeRepository,
                     TOTPService totpService) {
        this.userRepository = userRepository;
        this.mfaRepository = mfaRepository;
        this.backupCodeRepository = backupCodeRepository;
        this.totpService = totpService;
    }

    @Transactional
    public MFASetupResponse setupMFA(Long userId, String method) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        if (user.getMfa() != null) {
            throw new IllegalStateException("MFA already set up for user");
        }
        MFA mfa = new MFA();
        mfa.setUser(user);
        mfa.setMethod(MFAMethod.valueOf(method.toUpperCase()));
        String secret = null;
        if (MFAMethod.TOTP.name().equalsIgnoreCase(method)) {
            secret = totpService.generateSecret();
            mfa.setSecret(secret);
        } else if (MFAMethod.SMS.name().equalsIgnoreCase(method)) {
            // SMS setup requires phone number; assume set elsewhere or extend request
            throw new UnsupportedOperationException("SMS MFA not implemented yet");
        }
        mfa.setVerified(false);
        mfaRepository.save(mfa);

        List<String> backupCodes = generateBackupCodes(user);
        return new MFASetupResponse(secret, backupCodes);
    }

    public List<String> getBackupCodes(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        List<BackupCode> backupCodes = backupCodeRepository.findByUserUserIdAndUsedFalse(userId);
        return backupCodes.stream().map(BackupCode::getCode).collect(Collectors.toList());
    }
    
    public java.util.Optional<MFA> getMFAForUser(Long userId) {
        return mfaRepository.findByUserUserId(userId);
    }

    private List<String> generateBackupCodes(User user) {
        List<String> codes = new java.util.ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String code = RandomStringUtils.randomAlphanumeric(8);
            BackupCode backupCode = new BackupCode();
            backupCode.setUser(user);
            backupCode.setCode(code);
            backupCode.setUsed(false);
            backupCodeRepository.save(backupCode);
            codes.add(code);
        }
        return codes;
    }
}
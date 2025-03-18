package com.uxfx.usermanagement.controller;

import com.uxfx.usermanagement.dto.*;
import com.uxfx.usermanagement.service.AuthService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Registration request received for user: {}", request.getEmail());
        authService.register(request);
        return ResponseEntity.ok("Registration successful. Please check your email to verify.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/mfa/verify")
    public ResponseEntity<JwtResponse> verifyMFA(@Valid @RequestBody MFAVerifyRequest request) {
        JwtResponse response = authService.verifyMFA(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token format");
        }
        String token = authHeader.substring(7);
        authService.revokeToken(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        String newAccessToken = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(new JwtResponse(newAccessToken, request.getRefreshToken()));
    }
}

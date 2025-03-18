package com.uxfx.usermanagement.controller;

import com.uxfx.usermanagement.dto.EmailVerificationRequest;
import com.uxfx.usermanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EmailVerificationController {
    @Autowired
    private AuthService authService;

    @PostMapping("/email-verification/request")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> requestVerificationEmail(@Valid @RequestBody EmailVerificationRequest request) {
        authService.resendVerificationEmail(request.getIdentifier());
        return ResponseEntity.ok("Verification email sent");
    }

    @GetMapping("/email-verification/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully");
    }
}
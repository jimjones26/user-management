package com.uxfx.usermanagement.service;

public interface EmailService {
    void sendVerificationEmail(String to, String verificationLink);
    void sendPasswordResetEmail(String email, String token);
}
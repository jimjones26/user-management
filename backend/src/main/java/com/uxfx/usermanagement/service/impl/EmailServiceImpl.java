package com.uxfx.usermanagement.service.impl;

import com.uxfx.usermanagement.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendVerificationEmail(String to, String verificationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Please verify your email by clicking on the following link: " + verificationLink);
        emailSender.send(message);
    }
    
    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, please click on the following link: " + resetLink + 
                       "\n\nIf you did not request a password reset, please ignore this email.");
        emailSender.send(message);
    }
}

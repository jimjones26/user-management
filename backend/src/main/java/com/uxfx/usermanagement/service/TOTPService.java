package com.uxfx.usermanagement.service;

public interface TOTPService {
    /**
     * Generates a new secret key for TOTP authentication
     * @return the generated secret key
     */
    String generateSecret();
    
    /**
     * Generates a TOTP code based on the provided secret
     * @param secret the secret key
     * @return the generated TOTP code
     */
    String generateTOTP(String secret);
    
    /**
     * Validates if the provided TOTP code matches the expected code for the secret
     * @param secret the secret key
     * @param code the TOTP code to validate
     * @return true if the code is valid, false otherwise
     */
    boolean validateTOTP(String secret, String code);
}

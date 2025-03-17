package com.uxfx.usermanagement.dto;

import javax.validation.constraints.NotBlank;

public class PasswordResetRequest {

    @NotBlank(message = "Identifier is required")
    private String identifier;

    // Getters and Setters
    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
}
package com.uxfx.usermanagement.dto;

import javax.validation.constraints.NotBlank;

public class EmailVerificationRequest {
    @NotBlank(message = "Identifier is required")
    private String identifier;

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
}
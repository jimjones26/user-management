package com.uxfx.usermanagement.dto;

import javax.validation.constraints.NotBlank;

public class MFAVerifyRequest {
    @NotBlank(message = "Token is required")
    private String token;

    @NotBlank(message = "MFA code is required")
    private String code;

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
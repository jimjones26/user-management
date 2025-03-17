package com.uxfx.usermanagement.dto;

public class LoginResponse {
    private boolean mfaRequired;
    private String token;
    private String refreshToken;

    public LoginResponse(boolean mfaRequired, String token, String refreshToken) {
        this.mfaRequired = mfaRequired;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    // Getters
    public boolean isMfaRequired() { return mfaRequired; }
    public String getToken() { return token; }
    public String getRefreshToken() { return refreshToken; }
}
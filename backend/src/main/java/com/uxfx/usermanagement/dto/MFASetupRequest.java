package com.uxfx.usermanagement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MFASetupRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Method is required")
    private String method;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
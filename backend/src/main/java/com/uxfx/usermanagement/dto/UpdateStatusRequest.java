package com.uxfx.usermanagement.dto;

import javax.validation.constraints.NotNull;
import com.uxfx.usermanagement.model.UserStatus;

public class UpdateStatusRequest {
    @NotNull(message = "Status is required")
    private UserStatus status;

    // Getters and Setters
    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }
}

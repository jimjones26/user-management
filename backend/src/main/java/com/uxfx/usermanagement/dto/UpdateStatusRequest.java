package com.uxfx.usermanagement.dto;

import javax.validation.constraints.NotNull;
import com.uxfx.usermanagement.model.Status;

public class UpdateStatusRequest {
    @NotNull(message = "Status is required")
    private Status status;

    // Getters and Setters
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
package com.uxfx.usermanagement.dto;

import java.time.LocalDateTime;

public class AuditLogDto {
    private Long logId;
    private String action;
    private LocalDateTime timestamp;
    private String details;

    public Long getLogId() { return logId; }
    public void setLogId(Long logId) { this.logId = logId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}
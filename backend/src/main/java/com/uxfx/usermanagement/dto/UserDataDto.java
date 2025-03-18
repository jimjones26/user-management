package com.uxfx.usermanagement.dto;

import java.util.List;

public class UserDataDto {
    private UserDto user;
    private List<AuditLogDto> auditLogs;
    private List<UserCustomFieldValueDto> customFieldValues;

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
    public List<AuditLogDto> getAuditLogs() { return auditLogs; }
    public void setAuditLogs(List<AuditLogDto> auditLogs) { this.auditLogs = auditLogs; }
    public List<UserCustomFieldValueDto> getCustomFieldValues() { return customFieldValues; }
    public void setCustomFieldValues(List<UserCustomFieldValueDto> customFieldValues) { this.customFieldValues = customFieldValues; }
}
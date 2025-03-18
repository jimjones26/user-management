package com.uxfx.usermanagement.dto;

public class NotificationPreferencesDto {
    private boolean emailNotifications;
    private boolean inAppNotifications;

    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }
    public boolean isInAppNotifications() { return inAppNotifications; }
    public void setInAppNotifications(boolean inAppNotifications) { this.inAppNotifications = inAppNotifications; }
}
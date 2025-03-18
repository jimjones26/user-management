package com.uxfx.usermanagement.dto;
import com.uxfx.usermanagement.model.UserStatus;

public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private UserStatus status;
    private boolean emailVerified;
    private boolean mfaEnabled;
    private boolean emailNotifications;
    private boolean inAppNotifications;

    // Constructors
    public UserDto() {}

    public UserDto(Long userId, String username, String email, UserStatus status) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.status = status;
    }
    
    public UserDto(Long userId, String username, String email, UserStatus status,
                  boolean emailVerified, boolean mfaEnabled,
                  boolean emailNotifications, boolean inAppNotifications) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.status = status;
        this.emailVerified = emailVerified;
        this.mfaEnabled = mfaEnabled;
        this.emailNotifications = emailNotifications;
        this.inAppNotifications = inAppNotifications;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }
    
    public boolean isEmailVerified() { return emailVerified; }
    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
    public boolean isMfaEnabled() { return mfaEnabled; }
    public void setMfaEnabled(boolean mfaEnabled) { this.mfaEnabled = mfaEnabled; }
    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }
    public boolean isInAppNotifications() { return inAppNotifications; }
    public void setInAppNotifications(boolean inAppNotifications) { this.inAppNotifications = inAppNotifications; }
}

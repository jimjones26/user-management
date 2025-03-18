package com.uxfx.usermanagement.dto;
import com.uxfx.usermanagement.model.UserStatus;

public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private UserStatus status;

    // Constructors
    public UserDto() {}

    public UserDto(Long userId, String username, String email, UserStatus status) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.status = status;
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
}

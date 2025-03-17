package com.uxfx.usermanagement.dto;

import javax.validation.constraints.Email;

public class UpdateUserRequest {
    private String username;

    @Email(message = "Email must be valid")
    private String email;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
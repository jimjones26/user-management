package com.uxfx.usermanagement.dto;

import com.uxfx.usermanagement.model.Status;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.util.Set;

public class CreateUserRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private Status status;
    private Set<String> roles;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
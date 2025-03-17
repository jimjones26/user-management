package com.uxfx.usermanagement.dto;

import javax.validation.constraints.NotNull;

import java.util.Set;

public class UpdateRolesRequest {
    @NotNull(message = "Roles are required")
    private Set<String> roles;

    // Getters and Setters
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
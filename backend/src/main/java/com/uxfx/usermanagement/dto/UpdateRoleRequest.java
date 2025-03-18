package com.uxfx.usermanagement.dto;

import lombok.Data;

@Data
public class UpdateRoleRequest {
    private String name;
    private String description;
}
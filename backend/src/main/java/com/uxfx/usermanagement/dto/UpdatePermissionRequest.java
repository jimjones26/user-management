package com.uxfx.usermanagement.dto;

import lombok.Data;

@Data
public class UpdatePermissionRequest {
    private String name;
    private String description;
}
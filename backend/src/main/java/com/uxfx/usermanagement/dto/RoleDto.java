package com.uxfx.usermanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private String description;
    private List<PermissionDto> permissions;
}
package com.uxfx.usermanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRolePermissionsRequest {
    private List<Long> permissionIds;
}
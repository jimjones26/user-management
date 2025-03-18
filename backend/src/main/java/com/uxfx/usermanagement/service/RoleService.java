package com.uxfx.usermanagement.service;

import com.uxfx.usermanagement.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<RoleDto> getAllRoles(Pageable pageable);
    RoleDto createRole(CreateRoleRequest request);
    RoleDto getRoleById(Long id);
    RoleDto updateRole(Long id, UpdateRoleRequest request);
    void deleteRole(Long id);
    RoleDto updateRolePermissions(Long id, UpdateRolePermissionsRequest request);
}
package com.uxfx.usermanagement.service;

import com.uxfx.usermanagement.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionService {
    Page<PermissionDto> getAllPermissions(Pageable pageable);
    PermissionDto createPermission(CreatePermissionRequest request);
    PermissionDto getPermissionById(Long id);
    PermissionDto updatePermission(Long id, UpdatePermissionRequest request);
    void deletePermission(Long id);
}
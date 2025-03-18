package com.uxfx.usermanagement.service;

import com.uxfx.usermanagement.dto.*;
import com.uxfx.usermanagement.exception.ResourceNotFoundException;
import com.uxfx.usermanagement.model.Permission;
import com.uxfx.usermanagement.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<PermissionDto> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    @Transactional
    public PermissionDto createPermission(CreatePermissionRequest request) {
        Permission permission = new Permission();
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
        permission = permissionRepository.save(permission);
        return convertToDto(permission);
    }

    @Override
    public PermissionDto getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        return convertToDto(permission);
    }

    @Override
    @Transactional
    public PermissionDto updatePermission(Long id, UpdatePermissionRequest request) {
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        if (request.getName() != null) {
            permission.setName(request.getName());
        }
        if (request.getDescription() != null) {
            permission.setDescription(request.getDescription());
        }
        permission = permissionRepository.save(permission);
        return convertToDto(permission);
    }

    @Override
    @Transactional
    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
        permissionRepository.delete(permission);
    }

    private PermissionDto convertToDto(Permission permission) {
        PermissionDto dto = new PermissionDto();
        dto.setId(permission.getPermissionId());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());
        return dto;
    }
}
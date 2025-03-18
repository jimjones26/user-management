package com.uxfx.usermanagement.service.impl;

import com.uxfx.usermanagement.dto.*;
import com.uxfx.usermanagement.exception.ResourceNotFoundException;
import com.uxfx.usermanagement.model.Permission;
import com.uxfx.usermanagement.model.Role;
import com.uxfx.usermanagement.repository.PermissionRepository;
import com.uxfx.usermanagement.repository.RoleRepository;
import com.uxfx.usermanagement.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<RoleDto> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    @Transactional
    public RoleDto createRole(CreateRoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role = roleRepository.save(role);
        return convertToDto(role);
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return convertToDto(role);
    }

    @Override
    @Transactional
    public RoleDto updateRole(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        if (request.getName() != null) {
            role.setName(request.getName());
        }
        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }
        role = roleRepository.save(role);
        return convertToDto(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        roleRepository.delete(role);
    }

    @Override
    @Transactional
    public RoleDto updateRolePermissions(Long id, UpdateRolePermissionsRequest request) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        Set<Permission> permissions = permissionRepository.findAllById(request.getPermissionIds())
            .stream().collect(Collectors.toSet());
        role.setPermissions(permissions);
        role = roleRepository.save(role);
        return convertToDto(role);
    }

    private RoleDto convertToDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getRoleId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setPermissions(role.getPermissions().stream()
            .map(this::convertPermissionToDto)
            .collect(Collectors.toList()));
        return dto;
    }

    private PermissionDto convertPermissionToDto(Permission permission) {
        PermissionDto dto = new PermissionDto();
        dto.setId(permission.getPermissionId());
        dto.setName(permission.getName());
        dto.setDescription(permission.getDescription());
        return dto;
    }
}
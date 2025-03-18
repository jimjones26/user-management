package com.uxfx.usermanagement.controller;

import com.uxfx.usermanagement.dto.*;
import com.uxfx.usermanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Page<RoleDto>> getAllRoles(Pageable pageable) {
        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody CreateRoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long roleId, @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(roleId, request));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roleId}/permissions")
    public ResponseEntity<RoleDto> updateRolePermissions(
        @PathVariable Long roleId, @RequestBody UpdateRolePermissionsRequest request) {
        return ResponseEntity.ok(roleService.updateRolePermissions(roleId, request));
    }
}
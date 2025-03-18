package com.uxfx.usermanagement.controller;

import com.uxfx.usermanagement.dto.*;
import com.uxfx.usermanagement.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public ResponseEntity<Page<PermissionDto>> getAllPermissions(Pageable pageable) {
        return ResponseEntity.ok(permissionService.getAllPermissions(pageable));
    }

    @PostMapping
    public ResponseEntity<PermissionDto> createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        return ResponseEntity.ok(permissionService.createPermission(request));
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionDto> getPermission(@PathVariable Long permissionId) {
        return ResponseEntity.ok(permissionService.getPermissionById(permissionId));
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<PermissionDto> updatePermission(
        @PathVariable Long permissionId, @RequestBody UpdatePermissionRequest request) {
        return ResponseEntity.ok(permissionService.updatePermission(permissionId, request));
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long permissionId) {
        permissionService.deletePermission(permissionId);
        return ResponseEntity.noContent().build();
    }
}
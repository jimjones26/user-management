package com.uxfx.usermanagement.controller;

import com.uxfx.usermanagement.dto.CreateUserRequest;
import com.uxfx.usermanagement.dto.UpdateRolesRequest;
import com.uxfx.usermanagement.dto.UpdateStatusRequest;
import com.uxfx.usermanagement.dto.UpdateUserRequest;
import com.uxfx.usermanagement.dto.UserDto;
import com.uxfx.usermanagement.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDto> users = userService.getAllUsers(status, pageable);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDto createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId, Authentication authentication) {
        UserDto user = userService.getUserById(userId);
        // Simplified permission: admins or self can view
        String currentUsername = authentication.getName();
        if (!user.getUsername().equals(currentUsername) &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request,
            Authentication authentication) {
        UserDto user = userService.getUserById(userId);
        String currentUsername = authentication.getName();
        if (!user.getUsername().equals(currentUsername) &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        UserDto updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.softDeleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUserStatus(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateStatusRequest request) {
        UserDto updatedUser = userService.updateUserStatus(userId, request.getStatus());
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUserRoles(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateRolesRequest request) {
        UserDto updatedUser = userService.updateUserRoles(userId, request.getRoles());
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}/data")
    public ResponseEntity<UserDto> getUserData(@PathVariable Long userId, Authentication authentication) {
        UserDto user = userService.getUserById(userId);
        String currentUsername = authentication.getName();
        if (!user.getUsername().equals(currentUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // Simplified GDPR data: returns UserDto (extend as needed)
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{userId}/delete-request")
    public ResponseEntity<Void> submitDeleteRequest(
            @PathVariable Long userId,
            Authentication authentication) {
        UserDto user = userService.getUserById(userId);
        String currentUsername = authentication.getName();
        if (!user.getUsername().equals(currentUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userService.softDeleteUser(userId);
        return ResponseEntity.accepted().build();
    }

    // Placeholder for notification endpoints (requires additional entity/modeling)
    @GetMapping("/{userId}/notifications")
    public ResponseEntity<String> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok("Notification preferences not implemented yet");
    }

    @PutMapping("/{userId}/notifications")
    public ResponseEntity<String> updateNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok("Notification preferences not implemented yet");
    }
}
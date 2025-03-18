package com.uxfx.usermanagement.controller;

import com.uxfx.usermanagement.dto.*;
import com.uxfx.usermanagement.service.UserService;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    @PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request) {
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
    @PreAuthorize("hasRole('ADMIN') or #userId == principal.userId")
    public ResponseEntity<UserDataDto> getUserData(@PathVariable Long userId) {
        UserDataDto userData = userService.getUserData(userId);
        return ResponseEntity.ok(userData);
    }

    @PostMapping("/{userId}/delete-request")
    @PreAuthorize("#userId == principal.userId")
    public ResponseEntity<Void> submitDeleteRequest(@PathVariable Long userId) {
        userService.softDeleteUser(userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{userId}/notifications")
    @PreAuthorize("#userId == principal.userId")
    public ResponseEntity<NotificationPreferencesDto> getNotifications(@PathVariable Long userId) {
        NotificationPreferencesDto preferences = userService.getNotificationPreferences(userId);
        return ResponseEntity.ok(preferences);
    }

    @PutMapping("/{userId}/notifications")
    @PreAuthorize("#userId == principal.userId")
    public ResponseEntity<Void> updateNotifications(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateNotificationPreferencesRequest request) {
        userService.updateNotificationPreferences(userId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/import")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> importUsers(@RequestParam("file") MultipartFile file) {
        userService.importUsers(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportUsers() {
        byte[] csvData = userService.exportUsers();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "users.csv");
        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }
}

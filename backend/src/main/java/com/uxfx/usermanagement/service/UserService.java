package com.uxfx.usermanagement.service;

import com.uxfx.usermanagement.dto.CreateUserRequest;
import com.uxfx.usermanagement.dto.UpdateUserRequest;
import com.uxfx.usermanagement.dto.UserDto;
import com.uxfx.usermanagement.model.Role;
import com.uxfx.usermanagement.model.Status;
import com.uxfx.usermanagement.model.User;
import com.uxfx.usermanagement.repository.RoleRepository;
import com.uxfx.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<UserDto> getAllUsers(String status, Pageable pageable) {
        Page<User> users;
        if (status != null && !status.isEmpty()) {
            Status enumStatus = Status.valueOf(status.toUpperCase());
            users = userRepository.findByStatus(enumStatus, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users.map(this::convertToDto);
    }

    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setStatus(request.getStatus() != null ? request.getStatus() : Status.ACTIVE);

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> roles = roleRepository.findByNameIn(request.getRoles());
            if (roles.size() != request.getRoles().size()) {
                throw new RuntimeException("One or more roles not found");
            }
            user.setRoles(roles);
        }

        user = userRepository.save(user);
        return convertToDto(user);
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    public UserDto updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            if (userRepository.existsByUsername(request.getUsername()) &&
                    !request.getUsername().equals(user.getUsername())) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (userRepository.existsByEmail(request.getEmail()) &&
                    !request.getEmail().equals(user.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }

        user = userRepository.save(user);
        return convertToDto(user);
    }

    public void softDeleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(Status.DELETED);
        user.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
    }

    public UserDto updateUserStatus(Long userId, Status status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        user = userRepository.save(user);
        return convertToDto(user);
    }

    public UserDto updateUserRoles(Long userId, Set<String> roleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Set<Role> roles = roleRepository.findByNameIn(roleNames);
        if (roles.size() != roleNames.size()) {
            throw new RuntimeException("One or more roles not found");
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return convertToDto(user);
    }

    public UserDto convertToDto(User user) {
        return new UserDto(user.getUserId(), user.getUsername(), user.getEmail(), user.getStatus());
    }
}
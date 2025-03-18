package com.uxfx.usermanagement.service;

import com.uxfx.usermanagement.dto.*;
import com.uxfx.usermanagement.model.*;
import com.uxfx.usermanagement.repository.*;
import com.opencsv.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuditLogRepository auditLogRepository;
    private final UserCustomFieldValueRepository userCustomFieldValueRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                      RoleRepository roleRepository,
                      AuditLogRepository auditLogRepository,
                      UserCustomFieldValueRepository userCustomFieldValueRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.auditLogRepository = auditLogRepository;
        this.userCustomFieldValueRepository = userCustomFieldValueRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        user.setDeletedAt(LocalDateTime.now());
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

    public UserDataDto getUserData(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<AuditLog> auditLogs = auditLogRepository.findByUserId(userId);
        List<UserCustomFieldValue> customFieldValues = userCustomFieldValueRepository.findByUserId(userId);

        UserDto userDto = convertToDto(user);
        List<AuditLogDto> auditLogDtos = auditLogs.stream()
                .map(this::convertToAuditLogDto)
                .collect(Collectors.toList());
        List<UserCustomFieldValueDto> customFieldValueDtos = customFieldValues.stream()
                .map(this::convertToUserCustomFieldValueDto)
                .collect(Collectors.toList());

        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setUser(userDto);
        userDataDto.setAuditLogs(auditLogDtos);
        userDataDto.setCustomFieldValues(customFieldValueDtos);

        return userDataDto;
    }

    public NotificationPreferencesDto getNotificationPreferences(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        NotificationPreferencesDto dto = new NotificationPreferencesDto();
        dto.setEmailNotifications(user.isEmailNotifications());
        dto.setInAppNotifications(user.isInAppNotifications());
        return dto;
    }

    public void updateNotificationPreferences(Long userId, UpdateNotificationPreferencesRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setEmailNotifications(request.isEmailNotifications());
        user.setInAppNotifications(request.isInAppNotifications());
        userRepository.save(user);
    }

    public void importUsers(MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReaderBuilder(reader)
                     .withCSVParser(new CSVParserBuilder().withSeparator(',').build())
                     .withSkipLines(1)
                     .build()) {

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                String username = line[0];
                String email = line[1];
                String password = line[2];
                String status = line[3];
                String rolesStr = line[4];
                List<String> roles = Arrays.asList(rolesStr.split(","));

                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setPasswordHash(passwordEncoder.encode(password));
                user.setStatus(Status.valueOf(status.toUpperCase()));
                user.setEmailVerified(false);
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());

                Set<Role> roleSet = roleRepository.findByNameIn(roles).stream().collect(Collectors.toSet());
                user.setRoles(roleSet);

                userRepository.save(user);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to import users: " + e.getMessage(), e);
        }
    }

    public byte[] exportUsers() {
        List<User> users = userRepository.findAll();
        try (StringWriter writer = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(writer)) {

            csvWriter.writeNext(new String[]{"username", "email", "status", "roles"});

            for (User user : users) {
                String roles = user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(","));

                csvWriter.writeNext(new String[]{
                        user.getUsername(),
                        user.getEmail(),
                        user.getStatus().name(),
                        roles
                });
            }

            return writer.toString().getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to export users: " + e.getMessage(), e);
        }
    }

    private AuditLogDto convertToAuditLogDto(AuditLog auditLog) {
        AuditLogDto dto = new AuditLogDto();
        dto.setLogId(auditLog.getLogId());
        dto.setAction(auditLog.getAction());
        dto.setTimestamp(auditLog.getTimestamp());
        dto.setDetails(auditLog.getDetails());
        return dto;
    }

    private UserCustomFieldValueDto convertToUserCustomFieldValueDto(UserCustomFieldValue value) {
        UserCustomFieldValueDto dto = new UserCustomFieldValueDto();
        dto.setValueId(value.getValueId());
        dto.setFieldId(value.getField().getFieldId());
        dto.setFieldName(value.getField().getName());
        dto.setValueText(value.getValueText());
        dto.setValueNumber(value.getValueNumber());
        dto.setValueDate(value.getValueDate());
        return dto;
    }
}

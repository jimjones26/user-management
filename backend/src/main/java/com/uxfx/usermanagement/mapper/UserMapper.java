package com.uxfx.usermanagement.mapper;

import com.uxfx.usermanagement.dto.UserDto;
import com.uxfx.usermanagement.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        
        return new UserDto(
            user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            user.getStatus(),
            user.isEmailVerified(),
            user.isMfaEnabled(),
            user.isEmailNotifications(),
            user.isInAppNotifications()
        );
    }
}
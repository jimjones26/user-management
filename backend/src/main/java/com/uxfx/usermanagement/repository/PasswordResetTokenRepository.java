package com.uxfx.usermanagement.repository;

import com.uxfx.usermanagement.model.PasswordResetToken;
import com.uxfx.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUserAndIsUsedFalse(User user);
}
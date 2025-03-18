package com.uxfx.usermanagement.repository;

import com.uxfx.usermanagement.model.BackupCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BackupCodeRepository extends JpaRepository<BackupCode, Long> {
    List<BackupCode> findByUserIdAndIsUsedFalse(Long userId);
}
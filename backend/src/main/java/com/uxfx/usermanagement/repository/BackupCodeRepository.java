package com.uxfx.usermanagement.repository;

import com.uxfx.usermanagement.model.BackupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackupCodeRepository extends JpaRepository<BackupCode, Long> {
    List<BackupCode> findByUserUserIdAndIsUsedFalse(Long userId);
}

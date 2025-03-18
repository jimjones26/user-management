package com.uxfx.usermanagement.repository;

import com.uxfx.usermanagement.model.BackupCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackupCodeRepository extends JpaRepository<BackupCode, Long>, JpaSpecificationExecutor<BackupCode> {
    List<BackupCode> findByUserUserIdAndIsUsedFalse(Long userId);
}

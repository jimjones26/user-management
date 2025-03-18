package com.uxfx.usermanagement.repository;

import com.uxfx.usermanagement.model.MFA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface MFARepository extends JpaRepository<MFA, Long>, JpaSpecificationExecutor<MFA> {
    Optional<MFA> findByUserUserId(Long userId);
}

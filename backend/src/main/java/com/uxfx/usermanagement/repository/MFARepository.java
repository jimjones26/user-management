package com.uxfx.usermanagement.repository;

import com.uxfx.usermanagement.model.MFA;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MFARepository extends JpaRepository<MFA, Long> {
    Optional<MFA> findByUserId(Long userId);
}
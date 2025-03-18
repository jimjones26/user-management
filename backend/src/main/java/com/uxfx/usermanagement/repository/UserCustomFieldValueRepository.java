package com.uxfx.usermanagement.repository;

import com.uxfx.usermanagement.model.UserCustomFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCustomFieldValueRepository extends JpaRepository<UserCustomFieldValue, Long> {
    List<UserCustomFieldValue> findByUserId(Long userId);
}
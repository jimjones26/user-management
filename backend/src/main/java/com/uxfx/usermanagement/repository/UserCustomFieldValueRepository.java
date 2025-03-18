package com.uxfx.usermanagement.repository;

import com.uxfx.usermanagement.model.UserCustomFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCustomFieldValueRepository extends JpaRepository<UserCustomFieldValue, Long> {
    List<UserCustomFieldValue> findByUserUserId(Long userId);
}

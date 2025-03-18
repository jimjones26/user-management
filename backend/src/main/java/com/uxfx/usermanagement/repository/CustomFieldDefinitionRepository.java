package com.uxfx.usermanagement.repository;

import com.uxfx.usermanagement.model.CustomFieldDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFieldDefinitionRepository extends JpaRepository<CustomFieldDefinition, Long> {
}
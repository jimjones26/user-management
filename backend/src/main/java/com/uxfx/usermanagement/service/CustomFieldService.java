package com.uxfx.usermanagement.service;

import com.uxfx.usermanagement.dto.CreateCustomFieldRequest;
import com.uxfx.usermanagement.dto.UpdateCustomFieldRequest;
import com.uxfx.usermanagement.exception.ResourceNotFoundException;
import com.uxfx.usermanagement.model.CustomFieldDefinition;
import com.uxfx.usermanagement.repository.CustomFieldDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomFieldService {
    @Autowired
    private CustomFieldDefinitionRepository customFieldDefinitionRepository;

    public List<CustomFieldDefinition> getAllCustomFields() {
        return customFieldDefinitionRepository.findAll();
    }

    public CustomFieldDefinition createCustomField(CreateCustomFieldRequest request) {
        CustomFieldDefinition field = new CustomFieldDefinition();
        field.setName(request.getName());
        field.setType(request.getType());
        field.setRequired(request.isRequired());
        return customFieldDefinitionRepository.save(field);
    }

    public CustomFieldDefinition getCustomField(Long fieldId) {
        return customFieldDefinitionRepository.findById(fieldId)
                .orElseThrow(() -> new ResourceNotFoundException("Custom field not found with id: " + fieldId));
    }

    public CustomFieldDefinition updateCustomField(Long fieldId, UpdateCustomFieldRequest request) {
        CustomFieldDefinition field = customFieldDefinitionRepository.findById(fieldId)
                .orElseThrow(() -> new ResourceNotFoundException("Custom field not found with id: " + fieldId));
        field.setName(request.getName());
        field.setType(request.getType());
        field.setRequired(request.isRequired());
        return customFieldDefinitionRepository.save(field);
    }

    public void deleteCustomField(Long fieldId) {
        CustomFieldDefinition field = customFieldDefinitionRepository.findById(fieldId)
                .orElseThrow(() -> new ResourceNotFoundException("Custom field not found with id: " + fieldId));
        customFieldDefinitionRepository.delete(field);
    }
}
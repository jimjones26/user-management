package com.uxfx.usermanagement.controller;

import com.uxfx.usermanagement.dto.CreateCustomFieldRequest;
import com.uxfx.usermanagement.dto.UpdateCustomFieldRequest;
import com.uxfx.usermanagement.model.CustomFieldDefinition;
import com.uxfx.usermanagement.service.CustomFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/custom-fields")
public class CustomFieldController {
    @Autowired
    private CustomFieldService customFieldService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomFieldDefinition>> getAllCustomFields() {
        List<CustomFieldDefinition> fields = customFieldService.getAllCustomFields();
        return ResponseEntity.ok(fields);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomFieldDefinition> createCustomField(@Valid @RequestBody CreateCustomFieldRequest request) {
        CustomFieldDefinition field = customFieldService.createCustomField(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(field);
    }

    @GetMapping("/{fieldId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomFieldDefinition> getCustomField(@PathVariable Long fieldId) {
        CustomFieldDefinition field = customFieldService.getCustomField(fieldId);
        return ResponseEntity.ok(field);
    }

    @PutMapping("/{fieldId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomFieldDefinition> updateCustomField(
            @PathVariable Long fieldId,
            @Valid @RequestBody UpdateCustomFieldRequest request) {
        CustomFieldDefinition field = customFieldService.updateCustomField(fieldId, request);
        return ResponseEntity.ok(field);
    }

    @DeleteMapping("/{fieldId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomField(@PathVariable Long fieldId) {
        customFieldService.deleteCustomField(fieldId);
        return ResponseEntity.noContent().build();
    }
}
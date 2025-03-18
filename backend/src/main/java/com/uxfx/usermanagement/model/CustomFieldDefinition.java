package com.uxfx.usermanagement.model;

import javax.persistence.*;

@Entity
public class CustomFieldDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldType type;

    private boolean isRequired = false;

    // Getters and setters
    public Long getFieldId() { return fieldId; }
    public void setFieldId(Long fieldId) { this.fieldId = fieldId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public FieldType getType() { return type; }
    public void setType(FieldType type) { this.type = type; }
    public boolean isRequired() { return isRequired; }
    public void setRequired(boolean isRequired) { this.isRequired = isRequired; }
}
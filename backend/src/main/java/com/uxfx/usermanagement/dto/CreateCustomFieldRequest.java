package com.uxfx.usermanagement.dto;

import com.uxfx.usermanagement.model.FieldType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCustomFieldRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private FieldType type;

    private boolean isRequired;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public FieldType getType() { return type; }
    public void setType(FieldType type) { this.type = type; }
    public boolean isRequired() { return isRequired; }
    public void setRequired(boolean isRequired) { this.isRequired = isRequired; }
}
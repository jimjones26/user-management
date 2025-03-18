package com.uxfx.usermanagement.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePermissionRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String description;
}
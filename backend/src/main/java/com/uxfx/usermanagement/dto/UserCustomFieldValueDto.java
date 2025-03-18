package com.uxfx.usermanagement.dto;

import java.time.LocalDate;

public class UserCustomFieldValueDto {
    private Long valueId;
    private Long fieldId;
    private String fieldName;
    private String valueText;
    private Double valueNumber;
    private LocalDate valueDate;

    public Long getValueId() { return valueId; }
    public void setValueId(Long valueId) { this.valueId = valueId; }
    public Long getFieldId() { return fieldId; }
    public void setFieldId(Long fieldId) { this.fieldId = fieldId; }
    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
    public String getValueText() { return valueText; }
    public void setValueText(String valueText) { this.valueText = valueText; }
    public Double getValueNumber() { return valueNumber; }
    public void setValueNumber(Double valueNumber) { this.valueNumber = valueNumber; }
    public LocalDate getValueDate() { return valueDate; }
    public void setValueDate(LocalDate valueDate) { this.valueDate = valueDate; }
}
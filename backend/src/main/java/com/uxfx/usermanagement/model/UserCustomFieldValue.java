package com.uxfx.usermanagement.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class UserCustomFieldValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valueId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private CustomFieldDefinition field;

    private String valueText;
    private Double valueNumber;
    private LocalDate valueDate;

    // Getters and setters
    public Long getValueId() { return valueId; }
    public void setValueId(Long valueId) { this.valueId = valueId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public CustomFieldDefinition getField() { return field; }
    public void setField(CustomFieldDefinition field) { this.field = field; }
    public String getValueText() { return valueText; }
    public void setValueText(String valueText) { this.valueText = valueText; }
    public Double getValueNumber() { return valueNumber; }
    public void setValueNumber(Double valueNumber) { this.valueNumber = valueNumber; }
    public LocalDate getValueDate() { return valueDate; }
    public void setValueDate(LocalDate valueDate) { this.valueDate = valueDate; }
}
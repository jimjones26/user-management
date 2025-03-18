package com.uxfx.usermanagement.model;

import javax.persistence.*;

@Entity
public class BackupCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backupCodeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String code;

    private boolean isUsed = false;

    // Getters and setters
    public Long getBackupCodeId() { return backupCodeId; }
    public void setBackupCodeId(Long backupCodeId) { this.backupCodeId = backupCodeId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public boolean isUsed() { return isUsed; }
    public void setUsed(boolean isUsed) { this.isUsed = isUsed; }
}
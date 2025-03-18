package com.uxfx.usermanagement.model;

import javax.persistence.*;

@Entity
@Table(name = "backup_codes")
public class BackupCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String code;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed = false;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public boolean isUsed() { return isUsed; }
    public void setUsed(boolean isUsed) { this.isUsed = isUsed; }
}

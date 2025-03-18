package com.uxfx.usermanagement.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "backup_codes")
@Data
public class BackupCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String code;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed;
}
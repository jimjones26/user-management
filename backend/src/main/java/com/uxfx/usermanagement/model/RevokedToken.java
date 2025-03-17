package com.uxfx.usermanagement.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "revoked_token")
public class RevokedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long revokedTokenId;

    @Column(nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date revokedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    // Getters and Setters
    public Long getRevokedTokenId() { return revokedTokenId; }
    public void setRevokedTokenId(Long revokedTokenId) { this.revokedTokenId = revokedTokenId; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Date getRevokedAt() { return revokedAt; }
    public void setRevokedAt(Date revokedAt) { this.revokedAt = revokedAt; }
    public Date getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Date expiresAt) { this.expiresAt = expiresAt; }
}
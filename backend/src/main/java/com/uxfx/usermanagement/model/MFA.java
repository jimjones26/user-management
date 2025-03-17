package com.uxfx.usermanagement.model;

import javax.persistence.*;

@Entity
@Table(name = "mfa")
public class MFA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mfaId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MFAMethod method;

    private String secret; // For TOTP

    private String phoneNumber; // For SMS

    @Column(nullable = false)
    private boolean isVerified;

    // Getters and Setters
    public Long getMfaId() { return mfaId; }
    public void setMfaId(Long mfaId) { this.mfaId = mfaId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public MFAMethod getMethod() { return method; }
    public void setMethod(MFAMethod method) { this.method = method; }
    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { this.isVerified = verified; }
}

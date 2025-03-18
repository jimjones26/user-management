package com.uxfx.usermanagement.model;

import javax.persistence.*;

@Entity
@Table(name = "mfa")
public class MFA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mfa_id")
    private Long mfaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MFAMethod method;

    @Column(name = "secret")
    private String secret;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_verified")
    private boolean isVerified;

    // Constructors
    public MFA() {}

    public MFA(User user, MFAMethod method) {
        this.user = user;
        this.method = method;
        this.isVerified = false;
    }

    // Getters and Setters
    public Long getMfaId() {
        return mfaId;
    }

    public void setMfaId(Long mfaId) {
        this.mfaId = mfaId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MFAMethod getMethod() {
        return method;
    }

    public void setMethod(MFAMethod method) {
        this.method = method;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
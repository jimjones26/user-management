package com.uxfx.usermanagement.dto;

import java.util.List;

public class MFASetupResponse {
    private String secret;
    private List<String> backupCodes;

    public MFASetupResponse(String secret, List<String> backupCodes) {
        this.secret = secret;
        this.backupCodes = backupCodes;
    }

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }
    public List<String> getBackupCodes() { return backupCodes; }
    public void setBackupCodes(List<String> backupCodes) { this.backupCodes = backupCodes; }
}
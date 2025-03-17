package com.uxfx.usermanagement.service;

public interface TOTPService {
    boolean verifyCode(String secret, String code);
}
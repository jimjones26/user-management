package com.uxfx.usermanagement.service.impl;

import com.uxfx.usermanagement.service.TOTPService;
import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;

@Service
public class TOTPServiceImpl implements TOTPService {
    private static final int SECRET_SIZE = 20;
    private static final String ALGORITHM = "HmacSHA1";
    private static final long TIME_STEP = 30L;
    private static final int CODE_DIGITS = 6;

    @Override
    public String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[SECRET_SIZE];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    @Override
    public String generateTOTP(String secret) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secret);
        long currentTime = Instant.now().getEpochSecond() / TIME_STEP;
        return calculateTOTP(bytes, currentTime);
    }

    @Override
    public boolean validateTOTP(String secret, String code) {
        if (code == null || code.length() != CODE_DIGITS) {
            return false;
        }

        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secret);
        long currentTime = Instant.now().getEpochSecond() / TIME_STEP;

        // Check current time window and adjacent windows
        for (int i = -1; i <= 1; i++) {
            String calculatedCode = calculateTOTP(bytes, currentTime + i);
            if (calculatedCode.equals(code)) {
                return true;
            }
        }
        return false;
    }

    private String calculateTOTP(byte[] secret, long time) {
        try {
            byte[] data = new byte[8];
            long value = time;
            for (int i = 8; i-- > 0; value >>>= 8) {
                data[i] = (byte) value;
            }

            SecretKeySpec signKey = new SecretKeySpec(secret, ALGORITHM);
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(signKey);
            byte[] hash = mac.doFinal(data);

            int offset = hash[hash.length - 1] & 0xf;
            long truncatedHash = 0;
            for (int i = 0; i < 4; ++i) {
                truncatedHash <<= 8;
                truncatedHash |= (hash[offset + i] & 0xff);
            }

            truncatedHash &= 0x7FFFFFFF;
            truncatedHash %= Math.pow(10, CODE_DIGITS);

            return String.format("%0" + CODE_DIGITS + "d", truncatedHash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error generating TOTP", e);
        }
    }
}
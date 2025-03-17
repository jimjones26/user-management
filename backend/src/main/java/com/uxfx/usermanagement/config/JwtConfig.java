package com.uxfx.usermanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for JWT authentication.
 * This class binds properties with the 'app.jwt' prefix from application.properties.
 */
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
@Validated
public class JwtConfig {
    
    /**
     * Secret key used for signing JWT tokens
     */
    private String secret;

    /**
     * Token expiration time in milliseconds
     */
    private long expiration;
    
    // Getters and setters
    public String getSecret() {
        return secret;
    }
    
    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    public long getExpiration() {
        return expiration;
    }
    
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}

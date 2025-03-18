package com.uxfx.usermanagement.security;

import com.uxfx.usermanagement.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;
    
    @Value("${jwt.temp.expiration:300000}") // 5 minutes default
    private long tempTokenExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getEncoder().encode(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        try {
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("userId", user.getUserId())
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating access token", e);
            throw new RuntimeException("Could not generate token");
        }
    }

    public String generateRefreshToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpiration);

        try {
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("userId", user.getUserId())
                    .claim("type", "refresh")
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating refresh token", e);
            throw new RuntimeException("Could not generate refresh token");
        }
    }

    public String generateTempToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tempTokenExpiration);

        try {
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("userId", user.getUserId())
                    .claim("type", "temp")
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating temporary token", e);
            throw new RuntimeException("Could not generate temporary token");
        }
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(sanitizeToken(token))
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new RuntimeException("JWT token is expired");
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token");
        }
    }

    public Claims validateTempToken(String token) {
        Claims claims = validateToken(token);
        if (!"temp".equals(claims.get("type"))) {
            throw new RuntimeException("Invalid temporary token type");
        }
        return claims;
    }

    private String sanitizeToken(String token) {
        // Remove any potential problematic characters
        return token.trim().replace(" ", "+");
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return validateToken(token);
        } catch (Exception e) {
            logger.error("Error extracting claims from token", e);
            return null;
        }
    }

    public Claims validateRefreshToken(String token) {
        Claims claims = validateToken(token);
        if (!"refresh".equals(claims.get("type"))) {
            throw new RuntimeException("Invalid refresh token type");
        }
        return claims;
    }
}
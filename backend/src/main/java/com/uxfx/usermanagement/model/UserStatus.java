package com.uxfx.usermanagement.model;

/**
 * Represents the possible states of a user account in the system.
 * This implementation is compatible with both PostgreSQL and H2 databases.
 */
public enum UserStatus {
    ACTIVE,
    INACTIVE,
    PENDING,
    SUSPENDED,
    DELETED
}

-- Create custom enum types used across tables
CREATE TYPE user_status AS ENUM ('active', 'inactive', 'deleted');
CREATE TYPE mfa_method AS ENUM ('totp', 'sms');
CREATE TYPE custom_field_type AS ENUM ('string', 'number', 'date');

-- Create table for roles
CREATE TABLE roles (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT
);

-- Create table for permissions
CREATE TABLE permissions (
    permission_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT
);

-- Create table for users
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    status user_status NOT NULL,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- Create junction table for user-role many-to-many relationship
CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Create junction table for role-permission many-to-many relationship
CREATE TABLE role_permissions (
    role_id INTEGER NOT NULL,
    permission_id INTEGER NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id)
);

-- Create table for audit logs
CREATE TABLE audit_logs (
    log_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    action VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    details JSONB,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create table for MFA settings (one-to-one with users)
CREATE TABLE mfas (
    mfa_id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL,
    method mfa_method NOT NULL,
    secret VARCHAR(100),
    phone_number VARCHAR(20),
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create table for backup codes
CREATE TABLE backup_codes (
    backup_code_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    code VARCHAR(50) NOT NULL,
    is_used BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create table for custom field definitions
CREATE TABLE custom_field_definitions (
    field_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type custom_field_type NOT NULL,
    is_required BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create table for user custom field values
CREATE TABLE user_custom_field_values (
    value_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    field_id INTEGER NOT NULL,
    value_text VARCHAR(255),
    value_number NUMERIC,
    value_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (field_id) REFERENCES custom_field_definitions(field_id),
    UNIQUE (user_id, field_id)
);

-- Create table for password reset tokens
CREATE TABLE password_reset_tokens (
    reset_token_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    is_used BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create table for email verification tokens
CREATE TABLE email_verification_tokens (
    verification_token_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    is_used BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create table for revoked tokens (standalone)
CREATE TABLE revoked_tokens (
    revoked_token_id SERIAL PRIMARY KEY,
    token TEXT NOT NULL,
    revoked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL
);
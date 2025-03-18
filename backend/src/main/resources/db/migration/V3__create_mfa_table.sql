CREATE TABLE mfa (
    mfa_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    method VARCHAR(10) NOT NULL,
    secret VARCHAR(255),
    phone_number VARCHAR(20),
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_mfa_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT uk_mfa_user UNIQUE (user_id)
);
ALTER TABLE users ADD COLUMN reset_token VARCHAR(36);
ALTER TABLE users ADD COLUMN reset_token_expiry TIMESTAMP;
CREATE INDEX idx_reset_token ON users(reset_token);
-- Drop existing enum type if exists
DO $$ BEGIN
    IF EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_status') THEN
        ALTER TABLE users ALTER COLUMN status DROP DEFAULT;
        DROP TYPE user_status;
    END IF;
END $$;

-- Create enum type
CREATE TYPE user_status AS ENUM (
    'ACTIVE',
    'INACTIVE',
    'PENDING',
    'SUSPENDED',
    'DELETED'
);

-- Update existing column to use new enum type
ALTER TABLE users 
    ALTER COLUMN status TYPE user_status USING (
        CASE status 
            WHEN 'ACTIVE' THEN 'ACTIVE'::user_status
            WHEN 'INACTIVE' THEN 'INACTIVE'::user_status
            WHEN 'PENDING' THEN 'PENDING'::user_status
            WHEN 'SUSPENDED' THEN 'SUSPENDED'::user_status
            WHEN 'DELETED' THEN 'DELETED'::user_status
            ELSE 'PENDING'::user_status
        END
    );
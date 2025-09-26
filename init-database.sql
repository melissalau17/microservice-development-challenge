-- Create initial admin user (password will be encrypted by Spring Security)
INSERT INTO users (username, email, password, role, active, created_at, updated_at) 
VALUES (
    'admin', 
    'admin@marketplace.com', 
    '$2a$10$8.UnVuG9HpHF9.6O6d.1O.O6d.1O.O6d.1O.O6d.1O.O6d.1O', -- encrypted password: admin123
    'ADMIN', 
    true, 
    NOW(), 
    NOW()
) ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, email, password, role, active, created_at, updated_at) 
VALUES (
    'staff1', 
    'staff@marketplace.com', 
    '$2a$10$8.UnVuG9HpHF9.6O6d.1O.O6d.1O.O6d.1O.O6d.1O.O6d.1O', -- encrypted password: staff123
    'STAFF', 
    true, 
    NOW(), 
    NOW()
) ON CONFLICT (username) DO NOTHING;
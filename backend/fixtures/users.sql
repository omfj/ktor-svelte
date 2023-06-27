INSERT INTO users (id, username, email, hashed_password, created_at, updated_at) VALUES
    (gen_random_uuid(), 'someusername', 'user1@email.com', 'hashedpassword1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'ilovecats123', 'user2@email.com', 'hashedpassword2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

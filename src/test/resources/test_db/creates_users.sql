MERGE INTO roles AS target
USING (VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN')) AS source(id, name)
    ON target.id = source.id
    WHEN NOT MATCHED THEN
INSERT (id, name) VALUES (source.id, source.name);

-- Insert users data
INSERT INTO users (id, username, password, email)
VALUES (1, 'user', '$2a$12$I8sfXSRHtfYtJ4x7AmeKO.grcQFdz6UONbFuknDXK0i5UEslKOFMy', 'user@gmail.com'),
       (2, 'admin', '$2a$12$s0.nukt.h1a3KLrdsnmTkesaO07v/JkQnAzDoGp9AhCugLDcKqVZO', 'admin@gmail.com');

-- Insert users_roles data (do nothing if already exists)
MERGE INTO users_roles USING (
    VALUES (1, 1), (2, 2)
) AS source (user_id, role_id)
    ON users_roles.user_id = source.user_id AND users_roles.role_id = source.role_id
    WHEN NOT MATCHED THEN INSERT (user_id, role_id) VALUES (source.user_id, source.role_id);


-- Insert roles data
INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

-- Insert users data
INSERT INTO users (id, username, password, email)
VALUES (1, 'user', '$2a$12$I8sfXSRHtfYtJ4x7AmeKO.grcQFdz6UONbFuknDXK0i5UEslKOFMy', 'user@gmail.com'),
       (2, 'admin', '$2a$12$s0.nukt.h1a3KLrdsnmTkesaO07v/JkQnAzDoGp9AhCugLDcKqVZO', 'admin@gmail.com');

-- Insert users_roles data
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);

-- Insert task from admin
INSERT INTO tasks (id, task_name, description, status, priority, author_id, assignee_id, created_at, updated_at)
VALUES (1, 'Admin Task', 'This is a task created by the admin user.', 'IN_PROGRESS', 'HIGH', 1, 2, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

-- Insert task from user
INSERT INTO tasks (id, task_name, description, status, priority, author_id, assignee_id, created_at, updated_at)
VALUES (2, 'User Task', 'This is a task created by the regular user.', 'PENDING', 'MEDIUM', 2, 1, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

-- Insert comments for User Task
INSERT INTO comments (id, text, author_id, task_id, created_at, updated_at)
VALUES (1, 'Comment 1 for User Task', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'Comment 2 for User Task', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 'Comment 3 for User Task', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert comments for Admin Task
INSERT INTO comments (id, text, author_id, task_id, created_at, updated_at)
VALUES (4, 'Comment 1 for Admin Task', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 'Comment 2 for Admin Task', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, 'Comment 3 for Admin Task', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

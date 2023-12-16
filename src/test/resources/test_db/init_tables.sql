-- Drop tables if they exist
DROP TABLE IF EXISTS tasks_comments;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

-- Create roles table
CREATE TABLE roles
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Insert roles data
INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

-- Create users table
CREATE TABLE users
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255)        NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255)        NOT NULL
);

-- Insert users data
INSERT INTO users (id, username, password, email)
VALUES (1, 'user', '$2a$12$I8sfXSRHtfYtJ4x7AmeKO.grcQFdz6UONbFuknDXK0i5UEslKOFMy', 'user@gmail.com'),
       (2, 'admin', '$2a$12$s0.nukt.h1a3KLrdsnmTkesaO07v/JkQnAzDoGp9AhCugLDcKqVZO', 'admin@gmail.com');

-- Create users_roles table
CREATE TABLE users_roles
(
    user_id INT NOT NULL,
    role_id INT    NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Insert users_roles data
INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);

-- Create comments table
CREATE TABLE comments
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    text       VARCHAR(255),
    author_id  INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users (id)
);

-- Create tasks table
CREATE TABLE tasks
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    task_name   VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(50)  NOT NULL,
    priority    VARCHAR(50)  NOT NULL,
    author_id   INT,
    assignee_id INT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (assignee_id) REFERENCES users (id)
);

-- Insert task from admin
INSERT INTO tasks (id, task_name, description, status, priority, author_id, assignee_id, created_at, updated_at)
VALUES (1, 'Admin Task', 'This is a task created by the admin user.', 'IN_PROGRESS', 'HIGH', 1, 2, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

-- Insert task from user
INSERT INTO tasks (id, task_name, description, status, priority, author_id, assignee_id, created_at, updated_at)
VALUES (2, 'User Task', 'This is a task created by the regular user.', 'PENDING', 'MEDIUM', 2, 1, CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

-- Create tasks_comments table
CREATE TABLE tasks_comments
(
    task_id    INT NOT NULL,
    comment_id INT NOT NULL,
    PRIMARY KEY (task_id, comment_id),
    FOREIGN KEY (task_id) REFERENCES tasks (id),
    FOREIGN KEY (comment_id) REFERENCES comments (id)
);

-- Insert comments for User Task
INSERT INTO comments (id, text, author_id, created_at, updated_at)
VALUES (1, 'Comment 1 for User Task', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'Comment 2 for User Task', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 'Comment 3 for User Task', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert comments for Admin Task
INSERT INTO comments (id, text, author_id, created_at, updated_at)
VALUES (4, 'Comment 1 for Admin Task', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 'Comment 2 for Admin Task', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, 'Comment 3 for Admin Task', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert task comments
INSERT INTO tasks_comments (task_id, comment_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 4),
    (2, 5),
    (2, 6);

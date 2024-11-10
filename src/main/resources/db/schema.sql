DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,
                       name VARCHAR(50) NOT NULL,
                       surname VARCHAR(50),
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       phone VARCHAR(20),
                       role VARCHAR(20) NOT NULL,
                       CONSTRAINT users_chk_role CHECK (role IN ('ADMIN', 'USER'))
);

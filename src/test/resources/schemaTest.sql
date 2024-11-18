SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     name VARCHAR(128),
    surname VARCHAR(128),
    password VARCHAR(255),
    email VARCHAR(128) UNIQUE,
    phone VARCHAR(128),
    role VARCHAR(64)
    );

-- Таблица categories
CREATE TABLE IF NOT EXISTS categories (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          name VARCHAR(128)
    );

-- Таблица books
CREATE TABLE IF NOT EXISTS books (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     title VARCHAR(128),
                                     author VARCHAR(128),
                                     description TEXT,
                                     price DOUBLE,
                                     price_discount DOUBLE,
                                     discount INT,
                                     category_id BIGINT,
                                     total_stock INT,
                                     image_link VARCHAR(128),
                                     FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Таблица favorites
CREATE TABLE IF NOT EXISTS favorites (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         user_id BIGINT,
                                         book_id BIGINT,
                                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
    );

-- Таблица carts
CREATE TABLE IF NOT EXISTS carts (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     user_id BIGINT,
                                     FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- Таблица cart_items
CREATE TABLE IF NOT EXISTS cart_items (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                          cart_id BIGINT,
                                          book_id BIGINT,
                                          quantity INT,
                                          FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
    );

-- Таблица orders
CREATE TABLE IF NOT EXISTS orders (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      user_id BIGINT,
                                      delivery_address VARCHAR(500),
    contact_phone VARCHAR(128),
    delivery_method VARCHAR(128),
    -- status ENUM('pending', 'shipped', 'delivered', 'canceled'),
    status VARCHAR(128),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS order_items (
                                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                           order_id BIGINT,
                                           book_id BIGINT,
                                           quantity INT,
                                           price_at_purchase DOUBLE,
                                           FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
    );



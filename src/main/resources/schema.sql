USE bookstore;

-- Удаление таблиц в нужном порядке
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- Таблица users
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     name VARCHAR(128),
                                     surname VARCHAR(128),
                                     password VARCHAR(128),
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
                                     discount INT,
                                     category_id BIGINT,
                                     total INT,
                                     image VARCHAR(128),
                                     FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
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
                                      status ENUM('pending', 'shipped', 'delivered', 'canceled'),
                                      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Таблица order_items
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

-- Таблица favorites
CREATE TABLE IF NOT EXISTS favorites (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         user_id BIGINT,
                                         book_id BIGINT,
                                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                         FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);
-- Вставка пользователей
-- Пароль: password123
INSERT INTO users (id, created_at, updated_at, name, surname, password, email, phone, role)
VALUES
    (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Yuri', 'Gagarin', '$2b$12$cV.JacX0VnZA.wi7K1wGrurAJm6phv26rN3aacOG5fbB72Z1SBrFW', 'user1@example.com', '1234567890', 'USER'),
    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Neil', 'Armstrong', '$2b$12$Dk2fsAk5h2ENDTua5LYbxeG3/VWOpPNLhJP51rEm9BHBlPDPCyrBK', 'user2@example.com', '2345678901', 'USER'),
    (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Valentina', 'Tereshkova', '$2b$12$Tdi61lowp7abwnibry1VXupUzGjyAYzl1iTJZxiQ8cwFzmIH/WLo6', 'user3@example.com', '3456789012', 'USER'),
    (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Buzz', 'Aldrin', '$2b$12$HRtsqlRLwnp54nvsNk8LKOVM9rkAHfa3FHlIVOx0Bihq8jaj5jiDG', 'user4@example.com', '4567890123', 'USER'),
    (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Alexei', 'Leonov', '$2b$12$P3OcUKXsOcfhsawnic2Fue/eALAnBtCLXzMxVQqlcDcb9SvuyDhSS', 'user5@example.com', '5678901234', 'USER'),
    (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Chris', 'Hadfield', '$2b$12$Y65OCemHtYUVuC.f/6nbTusl43nVs6oLVwQatCDQJDOvek/9ybRgO', 'admin@example.com', '6789012345', 'ADMIN');


-- Вставка категорий
INSERT INTO categories (id, created_at, name)
VALUES (1, CURRENT_TIMESTAMP, 'Science Fiction'),
       (2, CURRENT_TIMESTAMP, 'Horror'),
       (3, CURRENT_TIMESTAMP, 'Classic Literature'),
       (4, CURRENT_TIMESTAMP, 'Psychology'),
       (5, CURRENT_TIMESTAMP, 'Poetry'),
       (6, CURRENT_TIMESTAMP, 'Biography');

-- Вставка книг
INSERT INTO books (id, created_at, updated_at, title, author, description, price, discount, price_discount, category_id, total_stock, image_link)
VALUES (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'The Hitchhiker''s Guide to the Galaxy', 'Douglas Adams', 'A comedic science fiction series.', 10.99, 0, 10.99, 1, 100, 'hitchhikers_guide.jpg'),
       (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1984', 'George Orwell', 'A dystopian social science fiction novel and cautionary tale.', 8.99, 10, 8.09, 2, 50, '1984.jpg'),
       (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Fahrenheit 451', 'Ray Bradbury', 'A novel about a future society where books are outlawed.', 12.50, 5, 11.87 , 3, 30, 'fahrenheit_451.jpg'),
       (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Brave New World', 'Aldous Huxley', 'A novel about a technologically advanced dystopian society.', 9.99, 15, 8.5 , 2, 70, 'brave_new_world.jpg'),
       (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Dune', 'Frank Herbert', 'A science fiction novel set in a distant future amidst a huge interstellar empire.', 15.00, 20,12, 1, 25, 'dune.jpg'),
       (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Book With Max Discount 1', 'Author A', 'Description 1', 20.00, 0, 16.00, 1, 100, 'book1.jpg'),
       (7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Book With Max Discount 2', 'Author B', 'Description 2', 25.00, 20, 20.00, 1, 100, 'book2.jpg');


-- Вставка корзин
INSERT INTO carts (id, created_at, updated_at, user_id) VALUES
                                                            (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),  -- Корзина для пользователя с id = 1
                                                            (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),  -- Корзина для пользователя с id = 2
                                                            (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3),  -- Корзина для пользователя с id = 3
                                                            (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4),  -- Корзина для пользователя с id = 4
                                                            (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5);  -- Корзина для пользователя с id = 5

-- Вставка элементов корзин
INSERT INTO cart_items (id, created_at, updated_at, cart_id, book_id, quantity) VALUES
                                                                                    (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 2),  -- 2 копии книги 1 в корзине 1
                                                                                    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 1),  -- 1 копия книги 2 в корзине 1
                                                                                    (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 3),  -- 3 копии книги 3 в корзине 2
                                                                                    (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 4, 1),  -- 1 копия книги 4 в корзине 2
                                                                                    (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 5, 5),  -- 5 копий книги 5 в корзине 3
                                                                                    (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 1, 1);  -- 1 копия книги 1 в корзине 3

-- Вставка избранного
INSERT INTO favorites (id, created_at, user_id, book_id) VALUES
                                                             (1, CURRENT_TIMESTAMP, 1, 1),  -- Избранная книга 1 для пользователя с id = 1
                                                             (2, CURRENT_TIMESTAMP, 1, 2),  -- Избранная книга 2 для пользователя с id = 1
                                                             (3, CURRENT_TIMESTAMP, 2, 3),  -- Избранная книга 3 для пользователя с id = 2
                                                             (4, CURRENT_TIMESTAMP, 3, 4),  -- Избранная книга 4 для пользователя с id = 3
                                                             (5, CURRENT_TIMESTAMP, 4, 5),  -- Избранная книга 5 для пользователя с id = 4
                                                             (6, CURRENT_TIMESTAMP, 5, 1);  -- Избранная книга 1 для пользователя с id = 5

-- Вставка заказов
INSERT INTO orders (id, created_at, updated_at, user_id, delivery_address, contact_phone, delivery_method, status) VALUES
                                                                                                                       (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 'Unter den Linden 1, 10117 Berlin', '030-1234567', 'Standard', 'Pending'),
                                                                                                                       (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 'Marienplatz 1, 80331 München', '089-1234567', 'Express', 'Shipped'),
                                                                                                                       (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 'Königsallee 1, 40212 Düsseldorf', '0211-1234567', 'Standard', 'Delivered'),
                                                                                                                       (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 'Münsterstraße 1, 48151 Münster', '0251-1234567', 'Pickup', 'Canceled'),
                                                                                                                       (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 'Schildergasse 1, 50667 Köln', '0221-1234567', 'Standard', 'Pending');

-- Вставка элементов заказов
INSERT INTO order_items (id, created_at, updated_at, order_id, book_id, quantity, price_at_purchase) VALUES
                                                                                                         (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 2, 10.99),  -- 2 копии книги 1 в заказе 1 по цене 10.99
                                                                                                         (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 1, 8.99),   -- 1 копия книги 2 в заказе 1 по цене 8.99
                                                                                                         (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 3, 12.50),  -- 3 копии книги 3 в заказе 2 по цене 12.50
                                                                                                         (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 4, 1, 9.99),   -- 1 копия книги 4 в заказе 2 по цене 9.99
                                                                                                         (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 5, 5, 15.00),  -- 5 копий книги 5 в заказе 3 по цене 15.00
                                                                                                         (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 1, 1, 10.99);  -- 1 копия книги 1 в заказе 3 по цене 10.99

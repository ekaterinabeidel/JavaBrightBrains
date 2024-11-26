
-- all passwords : password123
INSERT INTO users (id, created_at, updated_at, name, surname, password, email, phone, role)
VALUES
    (1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Yuri', 'Gagarin', '$2b$12$cV.JacX0VnZA.wi7K1wGrurAJm6phv26rN3aacOG5fbB72Z1SBrFW', 'user1@example.com', '1234567890', 'USER'),
    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Neil', 'Armstrong', '$2b$12$Dk2fsAk5h2ENDTua5LYbxeG3/VWOpPNLhJP51rEm9BHBlPDPCyrBK', 'user2@example.com', '2345678901', 'USER'),
    (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Valentina', 'Tereshkova', '$2b$12$Tdi61lowp7abwnibry1VXupUzGjyAYzl1iTJZxiQ8cwFzmIH/WLo6', 'user3@example.com', '3456789012', 'USER'),
    (4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Buzz', 'Aldrin', '$2b$12$HRtsqlRLwnp54nvsNk8LKOVM9rkAHfa3FHlIVOx0Bihq8jaj5jiDG', 'user4@example.com', '4567890123', 'USER'),
    (5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Alexei', 'Leonov', '$2b$12$P3OcUKXsOcfhsawnic2Fue/eALAnBtCLXzMxVQqlcDcb9SvuyDhSS', 'user5@example.com', '5678901234', 'USER'),
    (6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Chris', 'Hadfield', '$2b$12$Y65OCemHtYUVuC.f/6nbTusl43nVs6oLVwQatCDQJDOvek/9ybRgO', 'admin@example.com', '6789012345', 'ADMIN'),
    (7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Alex', 'Leo', '$2b$12$S5cyztohMDjtIzZJctSk0e9CpuirwHglr.dL45WYtmPaXhyED.oC6', 'user7@example.com', '5678901234', 'USER');



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
       (7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Book With Max Discount 2', 'Author B', 'Description 2', 25.00, 20, 20.00, 1, 100, 'book2.jpg'),
       (8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Book 3', 'Author B', 'Description 3', 25.00, 50, 20.00, 1, 100, 'book2.jpg'),
       (9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Book 4', 'Author B', 'Description 4', 25.00, 50, 20.00, 1, 100, 'book2.jpg'),
       (10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Book 5', 'Author B', 'Description 5', 25.00, 30, 20.00, 1, 100, 'book2.jpg'),
       (11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Book 6', 'Author B', 'Description 6', 25.00, 10, 20.00, 1, 100, 'book2.jpg');


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
                                           (1, '2023-05-22 23:51:12', '2023-06-11 21:38:14', 6, 'Alexanderplatz 1, Berlin', '723-1063866', 'Standard', 'Delivered'),
                                           (2, '2023-09-23 19:27:14', '2023-10-06 11:47:46', 5, 'Unter den Linden 1, Berlin', '822-2430761', 'Express', 'Canceled'),
                                           (3, '2021-09-12 00:13:30', '2021-09-26 05:13:15', 1, 'Bahnhofstraße 5, Frankfurt', '109-5484928', 'Standard', 'Delivered'),
                                           (4, '2022-04-28 11:37:17', '2022-05-01 09:36:23', 4, 'Marienplatz 1, München', '750-8180970', 'Pickup', 'Delivered'),
                                           (5, '2021-10-26 12:33:21', '2021-11-04 05:13:50', 6, 'Platzl 9, München', '636-9500459', 'Express', 'Pending'),
                                           (6, '2024-03-17 06:55:05', '2021-04-02 07:20:03', 3, 'Unter den Linden 1, Berlin', '479-3263746', 'Pickup', 'Canceled'),
                                           (7, '2023-07-12 04:36:13', '2023-07-25 15:03:05', 2, 'Platzl 9, München', '497-1292566', 'Pickup', 'Pending'),
                                           (8, '2021-10-10 05:55:50', '2021-10-26 01:48:59', 1, 'Platzl 9, München', '364-5720562', 'Express', 'Delivered'),
                                           (9, '2021-05-11 10:07:15', '2021-05-13 06:54:42', 5, 'Bahnhofstraße 5, Frankfurt', '286-2782563', 'Pickup', 'Delivered'),
                                           (10, '2023-02-01 19:02:45', '2023-02-24 19:16:59', 4, 'Bahnhofstraße 5, Frankfurt', '855-4046660', 'Standard', 'Pending'),
                                           (11, '2023-12-17 15:20:32', '2023-12-19 18:22:46', 5, 'Schildergasse 1, Köln', '603-5021393', 'Standard', 'Pending'),
                                           (12, '2024-04-03 16:48:02', '2022-04-19 15:32:41', 6, 'Platzl 9, München', '928-1954618', 'Standard', 'Delivered'),
                                           (13, '2022-12-12 00:02:28', '2023-01-11 20:35:17', 5, 'Münsterstraße 1, Münster', '133-7579676', 'Express', 'Delivered'),
                                           (14, '2024-05-30 20:36:54', '2023-06-02 11:37:52', 4, 'Marienplatz 1, München', '330-2188860', 'Express', 'Canceled'),
                                           (15, '2023-04-20 09:11:49', '2023-05-07 22:16:12', 1, 'Schildergasse 1, Köln', '378-6625956', 'Express', 'Delivered'),
                                           (16, '2023-02-26 12:45:58', '2023-03-14 13:40:50', 1, 'Marienplatz 1, München', '472-9509912', 'Express', 'Delivered'),
                                           (17, '2022-04-13 04:12:47', '2022-04-24 07:10:46', 2, 'Marienplatz 1, München', '204-6686364', 'Pickup', 'Pending'),
                                           (18, '2021-10-23 06:57:51', '2021-11-09 22:44:01', 2, 'Königsallee 1, Düsseldorf', '102-9274595', 'Standard', 'Canceled'),
                                           (19, '2023-10-06 13:55:54', '2023-10-29 20:30:31', 1, 'Königsallee 1, Düsseldorf', '728-3292153', 'Pickup', 'Delivered'),
                                           (20, '2021-09-08 23:27:13', '2021-09-19 22:36:18', 2, 'Friedrichstraße 2, Stuttgart', '919-5753820', 'Standard', 'Canceled');

-- Вставка элементов заказов
INSERT INTO order_items (id, created_at, updated_at, order_id, book_id, quantity, price_at_purchase) VALUES
                         (1, '2023-02-14 15:34:45', '2023-02-20 12:22:31', 1, 3, 2, 9.99),
                         (2, '2022-07-08 10:17:23', '2022-07-10 08:55:14', 2, 5, 1, 15.50),
                         (3, '2023-03-25 18:48:10', '2023-03-26 14:32:05', 3, 1, 3, 8.99),
                         (4, '2023-09-11 09:13:29', '2023-09-14 11:42:50', 4, 7, 4, 12.49),
                         (5, '2021-11-19 12:01:22', '2021-11-20 16:07:45', 5, 2, 2, 10.00),
                         (6, '2022-01-03 17:18:37', '2022-01-05 18:23:54', 6, 4, 1, 14.99),
                         (7, '2023-05-19 08:34:25', '2023-05-20 09:45:32', 7, 6, 5, 16.75),
                         (8, '2023-08-14 21:10:15', '2023-08-18 11:55:21', 8, 3, 2, 9.99),
                         (9, '2022-06-12 14:03:44', '2022-06-15 10:45:38', 9, 1, 3, 8.49),
                         (10, '2021-12-25 19:22:11', '2021-12-27 22:18:06', 10, 7, 1, 13.25),
                         (11, '2023-04-11 16:17:32', '2023-04-13 12:33:28', 11, 5, 4, 11.99),
                         (12, '2022-09-21 08:45:39', '2022-09-22 14:08:14', 12, 2, 3, 14.50),
                         (13, '2021-10-03 07:28:40', '2021-10-06 09:17:20', 13, 4, 2, 10.99),
                         (14, '2023-01-15 20:12:25', '2023-01-17 08:27:19', 14, 6, 3, 15.99),
                         (15, '2023-07-29 09:31:42', '2023-07-30 17:14:21', 15, 3, 1, 9.25),
                         (16, '2022-12-10 06:45:19', '2022-12-15 11:35:30', 16, 5, 2, 16.99),
                         (17, '2022-11-19 19:37:49', '2022-11-21 15:24:56', 17, 7, 3, 12.75),
                         (18, '2023-06-03 14:55:22', '2023-06-05 17:08:45', 18, 2, 4, 11.50),
                         (19, '2021-09-09 22:33:41', '2021-09-10 13:20:39', 19, 1, 2, 9.75),
                         (20, '2023-10-11 18:42:33', '2023-10-14 08:30:45', 20, 6, 3, 14.99),
                         (21, '2022-05-01 07:15:24', '2022-05-03 11:12:40', 1, 11, 1, 10.00),
                         (22, '2022-08-19 13:35:50', '2022-08-20 15:30:12', 2, 3, 2, 9.49),
                         (23, '2023-02-28 08:22:14', '2023-03-01 12:15:11', 3, 9, 4, 12.75),
                         (24, '2023-03-19 15:33:55', '2023-03-21 16:42:49', 4, 7, 3, 11.99),
                         (25, '2023-06-12 09:25:19', '2023-06-14 07:45:10', 5, 10, 5, 10.25),
                         (26, '2023-04-23 18:54:13', '2023-04-25 21:14:59', 6, 6, 3, 9.99),
                         (27, '2022-02-17 10:22:35', '2022-02-19 14:55:44', 7, 4, 2, 15.99),
                         (28, '2023-12-11 13:50:22', '2023-12-12 12:35:11', 8, 6, 1, 14.49),
                         (29, '2022-09-13 22:35:19', '2022-09-15 09:22:14', 9, 8, 2, 10.75),
                         (30, '2021-11-21 14:45:52', '2021-11-23 13:38:30', 10, 3, 3, 8.99);

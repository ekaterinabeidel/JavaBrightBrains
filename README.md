# Online Book Store Backend

## Description

The Online Book Store backend provides functionality for both customers and administrators. Customers can browse the book catalog, add items to the cart, place orders, and track their status. Administrators can manage the book catalog, generate reports, and manage users.

### Database Diagram

![Database Schema](https://github.com/ekaterinabeidel/JavaBrightBrains/blob/main/diagram.png)

### Key Features

#### For Customers:
- Registration and authentication.
- Browse the book catalog with filtering, sorting options, and pagination.
- Add books to the cart and place orders.
- View purchase history.
- Manage favorite books.
- View and update user profile.

#### For Administrators:
- Manage books in the catalog: add, edit, delete.
- Manage book categories.
- Create discounts.
- Generate reports:
  - Top 10 most purchased products.
  - Top 10 canceled products.
  - Books that have been in "Pending Payment" status for more than N days.
  - Profit for the selected period with time-based grouping.

### Database Table Structure

#### `users` (User Table)
| Column Name    | Data Type     | Description                                         |
|----------------|---------------|-----------------------------------------------------|
| id             | BIGINT        | PRIMARY KEY, unique user identifier (auto-increment) |
| created_at     | TIMESTAMP     | Date and time the user was created                  |
| updated_at     | TIMESTAMP     | Date and time the user was last updated             |
| name           | VARCHAR(128)  | User's first name                                   |
| surname        | VARCHAR(128)  | User's last name                                    |
| password       | VARCHAR(128)  | User's hashed password                              |
| email          | VARCHAR(128)  | User's email address (unique value)                 |
| phone          | VARCHAR(128)  | User's contact phone number                         |
| role           | VARCHAR(64)   | User's role (e.g., admin, user)                     |

#### `categories` (Book Categories)
| Column Name    | Data Type     | Description                                         |
|----------------|---------------|-----------------------------------------------------|
| id             | BIGINT        | PRIMARY KEY, unique category identifier (auto-increment) |
| created_at     | TIMESTAMP     | Date and time the category was created              |
| name           | VARCHAR(128)  | Name of the category                                |

#### `books` (Books Catalog)
| Column Name    | Data Type     | Description                                         |
|----------------|---------------|-----------------------------------------------------|
| id             | BIGINT        | PRIMARY KEY, unique book identifier (auto-increment) |
| created_at     | TIMESTAMP     | Date and time the book was created                  |
| updated_at     | TIMESTAMP     | Date and time the book was last updated             |
| title          | VARCHAR(128)  | Title of the book                                   |
| author         | VARCHAR(128)  | Author of the book                                  |
| description    | TEXT          | Description of the book                             |
| price          | DOUBLE        | Price of the book                                   |
| price_discount | DOUBLE        | Final price after discount                          |
| discount       | INT           | Discount percentage for the book                    |
| category_id    | BIGINT        | FOREIGN KEY, reference to the `categories` table    |
| total_stock    | INT           | Total number of available copies                    |
| image_link     | VARCHAR(128)  | URL or path to the book's image                     |

#### `favorites` (Favorites Table)
| Column Name    | Data Type     | Description                                         |
|----------------|---------------|-----------------------------------------------------|
| id             | BIGINT        | PRIMARY KEY, unique favorite item identifier (auto-increment) |
| created_at     | TIMESTAMP     | Date and time the book was added to favorites       |
| user_id        | BIGINT        | FOREIGN KEY, reference to the `users` table        |
| book_id        | BIGINT        | FOREIGN KEY, reference to the `books` table        |

#### `carts` (Carts Table)
| Column Name    | Data Type     | Description                                         |
|----------------|---------------|-----------------------------------------------------|
| id             | BIGINT        | PRIMARY KEY, unique cart identifier (auto-increment) |
| created_at     | TIMESTAMP     | Date and time the cart was created                  |
| updated_at     | TIMESTAMP     | Date and time the cart was last updated             |
| user_id        | BIGINT        | FOREIGN KEY, reference to the `users` table        |

#### `cart_items` (Cart Items Table)
| Column Name    | Data Type     | Description                                         |
|----------------|---------------|-----------------------------------------------------|
| id             | BIGINT        | PRIMARY KEY, unique cart item identifier (auto-increment) |
| created_at     | TIMESTAMP     | Date and time the item was added                    |
| updated_at     | TIMESTAMP     | Date and time the item was last updated             |
| cart_id        | BIGINT        | FOREIGN KEY, reference to the `carts` table        |
| book_id        | BIGINT        | FOREIGN KEY, reference to the `books` table        |
| quantity       | INT           | Quantity of the book in the cart                    |

#### `orders` (Orders Table)
| Column Name     | Data Type     | Description                                                                  |
|-----------------|---------------|------------------------------------------------------------------------------|
| id              | BIGINT        | PRIMARY KEY, unique order identifier (auto-increment)                        |
| created_at      | TIMESTAMP     | Date and time the order was created                                          |
| updated_at      | TIMESTAMP     | Date and time the order was last updated                                     |
| user_id         | BIGINT        | FOREIGN KEY, reference to the `users` table                                  |
| delivery_address| VARCHAR(500)  | Delivery address                                                             |
| contact_phone   | VARCHAR(128)  | Contact phone number for delivery                                            |
| delivery_method | VARCHAR(128)  | Delivery method (e.g., standard, express)                                    |                                      |
| status          | VARCHAR(128)  | Order status (e.g., pending, paid, shipped, delivered, canceled, processing) |

#### `order_items` (Order Items Table)
| Column Name        | Data Type     | Description                                         |
|--------------------|---------------|-----------------------------------------------------|
| id                 | BIGINT        | PRIMARY KEY, unique order item identifier (auto-increment) |
| created_at         | TIMESTAMP     | Date and time the item was added                    |
| updated_at         | TIMESTAMP     | Date and time the item was last updated             |
| order_id           | BIGINT        | FOREIGN KEY, reference to the `orders` table       |
| book_id            | BIGINT        | FOREIGN KEY, reference to the `books` table        |
| quantity           | INT           | Quantity of the book in the order                   |
| price_at_purchase  | DOUBLE        | Price of the book at the time of purchase           |

### Project Structure

- `src/main/java/bookstore` — source code.
- `src/main/resources` — configuration files and resources.

### Used Technologies

- **Programming Language:** Java 21
- **Frameworks and Libraries:**
  - Spring Boot (Web, Data JPA, Security)
  - Hibernate
  - Lombok (to simplify code)
  - Swagger/OpenAPI (for API documentation)
- **Database:** MySQL
- **Containerization:** Docker

### Contacts

If you have any questions or suggestions, feel free to contact us:
- Ekaterina Beidel, ek.beidel@gmail.com, [GitHub](https://github.com/ekaterinabeidel)
- Svetlana Zhuravleva, karpenten.xo@gmail.com, [GitHub](https://github.com/ZhuravlevaS)
- Ivan Beidel, ivanbeidel@gmail.com, [GitHub](https://github.com/ivanbeidel)

package bookstore.javabrightbrains.dto;


import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
}

/**
 * {
 *     "name": "Admin",
 *     "surname": "User",
 *     "email": "admin@example.com",
 *     "phone": "1234567890",
 *     "password": "admin123"
 * }
 *
 * {
 *     "name": "John",
 *     "surname": "Doe",
 *     "email": "user@example.com",
 *     "phone": "0987654321",
 *     "password": "user123"
 * }
 */
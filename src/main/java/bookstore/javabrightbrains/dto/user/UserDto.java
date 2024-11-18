package bookstore.javabrightbrains.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {
    @NotNull(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Surname is required")
    @Size(min = 1, max = 50, message = "Name must be between 2 and 50 characters")
    private String surname;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Phone number is invalid")
    private String phone;
}

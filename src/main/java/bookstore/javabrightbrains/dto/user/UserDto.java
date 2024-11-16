package bookstore.javabrightbrains.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @Size(min = 1, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Size(min = 1, max = 50, message = "Name must be between 2 and 50 characters")
    private String surname;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Phone number is invalid")
    private String phone;
}

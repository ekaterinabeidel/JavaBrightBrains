package bookstore.javabrightbrains.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequestDto {
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(min = 1, max = 50, message = "Surname must be between 1 and 50 characters")
    private String surname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}

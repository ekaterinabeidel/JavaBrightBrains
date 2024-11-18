package bookstore.javabrightbrains.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequestDto {
    private String name;
    private String surname;
    private String email;
    private String password;
}

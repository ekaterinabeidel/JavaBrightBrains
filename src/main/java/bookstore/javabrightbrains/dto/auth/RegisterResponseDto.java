package bookstore.javabrightbrains.dto.auth;

import bookstore.javabrightbrains.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Role role;
}

package bookstore.javabrightbrains.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String email;
    private String jwtToken;
    private String refreshToken;
}

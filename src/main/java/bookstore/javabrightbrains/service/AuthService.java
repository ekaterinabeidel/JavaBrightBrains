package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.auth.*;

public interface AuthService {
    RegisterResponseDto register(RegisterRequestDto registerRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto);
}

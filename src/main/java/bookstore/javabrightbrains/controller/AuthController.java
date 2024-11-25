package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.annotation.AuthControllerTag;
import bookstore.javabrightbrains.annotation.LoginUser;
import bookstore.javabrightbrains.annotation.RefreshToken;
import bookstore.javabrightbrains.annotation.RegisterUser;
import bookstore.javabrightbrains.dto.auth.*;
import bookstore.javabrightbrains.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@AuthControllerTag
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @RegisterUser
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto responseDto = authService.register(registerRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    @LoginUser
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto responseDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/refresh")
    @RefreshToken
    public ResponseEntity<RefreshTokenResponseDto> refresh(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshTokenResponseDto refreshTokenResponseDto = authService.refresh(refreshTokenRequestDto);
        return ResponseEntity.ok(refreshTokenResponseDto);
    }
}

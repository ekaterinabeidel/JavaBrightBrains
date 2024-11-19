package bookstore.javabrightbrains.controller;


import bookstore.javabrightbrains.dto.auth.*;
import bookstore.javabrightbrains.entity.User;
import bookstore.javabrightbrains.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication Controller", description = "APIs for handling authentication and authorization processes")
public class AuthController {
    @Autowired
    private  AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user",
            description = "Register a new user by providing their details such as name, email, and password")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }

    @Operation(summary = "Authenticate a user",
            description = "Authenticate a user by validating the provided email and password")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh the access token",
            description = "Refresh the access token by providing a valid refresh token")
    public ResponseEntity<RefreshTokenResponseDto> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authService.refresh(refreshTokenRequestDto));
    }
}

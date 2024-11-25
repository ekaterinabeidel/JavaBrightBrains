package bookstore.javabrightbrains.service;


import bookstore.javabrightbrains.dto.auth.*;
import bookstore.javabrightbrains.entity.User;
import bookstore.javabrightbrains.enums.Role;
import bookstore.javabrightbrains.exception.EmailDuplicateException;
import bookstore.javabrightbrains.exception.EmptyJwtTokenException;
import bookstore.javabrightbrains.exception.InvalidJwtTokenException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.UserRepository;
import bookstore.javabrightbrains.utils.MappingUtils;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtSecurityService jwtSecurityService;
    @Autowired
    private  AuthenticationManager authenticationManager;

    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new EmailDuplicateException(MessagesException.DUPLICATED_EMAIL);
        }
        User user = MappingUtils.convertRegisterRequestDtoToEntity(registerRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return MappingUtils.convertEntityUserToRegisterResponseDto(savedUser);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()));

        User user = userRepository
                .findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(MessagesException.USER_NOT_FOUND));

        String token = jwtSecurityService.generateToken(user);
        String refreshToken = jwtSecurityService.generateRefreshToken(new HashMap<>(), user);

        return LoginResponseDto
                .builder()
                .email(loginRequestDto.getEmail())
                .jwtToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto) {
        String jwt = refreshTokenRequestDto.getRefreshToken();

        if (jwt == null || jwt.isBlank()) {
            throw new EmptyJwtTokenException(MessagesException.JWT_TOKEN_IS_MISSING_OR_EMPTY);
        }

        if (!jwt.contains(".") || jwt.split("\\.").length != 3) {
            throw new InvalidJwtTokenException(MessagesException.JWT_TOKEN_FORMAT_INVALID);
        }

        String email = jwtSecurityService.extractUsername(jwt);
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MessagesException.USER_NOT_FOUND));

        if (jwtSecurityService.validateToken(jwt, user)) {
            RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();

            refreshTokenResponseDto.setJwtToken(jwtSecurityService.generateToken(user));
            refreshTokenResponseDto.setRefreshToken(jwtSecurityService.generateRefreshToken(new HashMap<>(), user));

            return refreshTokenResponseDto;
        }

        return null;
    }
}

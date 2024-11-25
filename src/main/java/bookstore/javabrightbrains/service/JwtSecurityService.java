package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public interface JwtSecurityService {
    String generateToken(User userDetails);

    String generateRefreshToken(Map<String, String> claims, UserDetails userDetails);

    String extractUsername(String token);

    Date extractExpiration(String token);

    Date extractIssuedAt(String token);

    boolean isTokenExpired(String token);

    boolean validateToken(String token, UserDetails userDetails);
    void validateUserAccess(Long userId);
}

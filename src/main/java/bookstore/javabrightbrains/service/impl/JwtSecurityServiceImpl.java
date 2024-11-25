package bookstore.javabrightbrains.service.impl;

import bookstore.javabrightbrains.entity.User;
import bookstore.javabrightbrains.exception.AccessDeniedException;
import bookstore.javabrightbrains.repository.UserRepository;
import bookstore.javabrightbrains.service.JwtSecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtSecurityServiceImpl implements JwtSecurityService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(User userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateRefreshToken(Map<String, String> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Получение имени юзера (он же почта)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Когда срок действия заканчивается
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Метод проверки срока действия токена
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Метод для валидации токена
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }

    public void validateUserAccess(Long userId) {
        // Получение текущей аутентификации из SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = getEmail(authentication);

        // Проверяем, что userId и email связаны
        boolean userExists = userRepository.existsByIdAndEmail(userId, email);
        if (!userExists) {
            throw new AccessDeniedException("Access denied: User ID does not match the authenticated user");
        }
    }

    private static String getEmail(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Access denied: User is not authenticated");
        }

        // Получение email пользователя из Principal
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails)) {
            throw new AccessDeniedException("Access denied: Authentication principal is invalid");
        }

        return ((UserDetails) principal).getUsername();
    }
}

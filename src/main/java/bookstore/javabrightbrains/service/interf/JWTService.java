package bookstore.javabrightbrains.service.interf;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JWTService {
    String generateToken(UserDetails userDetails);
    String getUserNameFromToken(String token);
    boolean isTokeValid(String token, UserDetails userDetails);
}
package bookstore.javabrightbrains.service.interf;


import bookstore.javabrightbrains.dto.SignUpRequest;
import bookstore.javabrightbrains.entity.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
}

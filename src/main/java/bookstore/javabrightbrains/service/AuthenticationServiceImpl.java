package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.SignUpRequest;
import bookstore.javabrightbrains.entity.User;
import bookstore.javabrightbrains.repository.AppUserRepository;
import bookstore.javabrightbrains.service.interf.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder encoder;

    public User signUp(SignUpRequest signUpRequest){
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setEmail(signUpRequest.getEmail());
        user.setPhone(signUpRequest.getPhone());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return user;
    }
}

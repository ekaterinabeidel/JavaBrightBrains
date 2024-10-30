package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.entity.User;


import bookstore.javabrightbrains.exception.EmailDuplicateException;
import bookstore.javabrightbrains.exception.MessagesExceptions;
import bookstore.javabrightbrains.repository.AppUserRepository;
import bookstore.javabrightbrains.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtSecurityService jwtSecurityService;


    public UserDetailsService getDetailsService() {
        UserDetailsService detailsService = new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return appUserRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND));
            }
        };

        return detailsService;
    }

    public ResponseEntity<UserDto> updateUser(Long id, UserDto userDto) throws UsernameNotFoundException {
        User user = userRepository.updateUser(id, userDto);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!userDto.getEmail().equals(user.getEmail())) {
            User duplicateUser = userRepository.getUserByEmail(userDto.getEmail()).orElseGet(null);
            if (duplicateUser != null) {
                throw new EmailDuplicateException(MessagesExceptions.DUPLICATED_EMAIL);
            }
        }

        UserDto newUserDto = new UserDto();

        newUserDto.setEmail(user.getEmail());
        newUserDto.setSurname(user.getSurname());
        newUserDto.setName(user.getName());
        newUserDto.setPhone(user.getPhone());

        return ResponseEntity.ok(newUserDto);

    }

    public ResponseEntity<UserDto> getUserInfo(Long id) {
        User user = userRepository.getUser(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDto newUserDto = new UserDto();

        newUserDto.setEmail(user.getEmail());
        newUserDto.setSurname(user.getSurname());
        newUserDto.setName(user.getName());
        newUserDto.setPhone(user.getPhone());

        return ResponseEntity.ok(newUserDto);
    }

    public ResponseEntity<String> deleteUser(Long id) {
        User user = userRepository.getUser(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (userRepository.deleteUser(id)) {
            return ResponseEntity.ok("Success");
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

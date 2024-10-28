package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.entity.User;


import bookstore.javabrightbrains.exception.MessagesExceptions;
import bookstore.javabrightbrains.repository.AppUserRepository;
import bookstore.javabrightbrains.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
                User user = appUserRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(MessagesExceptions.userNotFound));
                return user;
            }
        };

        return detailsService;
    }

    public UserDto updateUser(Long id, UserDto userDto, String token) throws UsernameNotFoundException {
        User user = userRepository.updateUser(id, userDto);
        if (user == null) {
            throw new UsernameNotFoundException(MessagesExceptions.userNotFound);
        }
        if (!jwtSecurityService.validateToken(token, user)) {
            return null;
        }

        UserDto newUserDto = new UserDto();

        newUserDto.setEmail(user.getEmail());
        newUserDto.setSurname(user.getSurname());
        newUserDto.setName(userDto.getName());
        newUserDto.setPhone(user.getPhone());

        return newUserDto;

    }


}

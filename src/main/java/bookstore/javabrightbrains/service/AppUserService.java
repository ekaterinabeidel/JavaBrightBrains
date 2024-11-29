package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.user.UserDto;

import bookstore.javabrightbrains.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService {
    UserDetailsService getDetailsService();
    UserDto updateUser(Long userId, UserDto userDto);
    UserDto getUserInfo(Long userId);
    void deleteUser(Long userId);
    User getUserById(Long userId);
}

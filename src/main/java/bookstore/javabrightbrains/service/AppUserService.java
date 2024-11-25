package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.user.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AppUserService {
    UserDetailsService getDetailsService();
    UserDto updateUser(Long userId, UserDto userDto);
    UserDto getUserInfo(Long id);
    void deleteUser(Long id);

}

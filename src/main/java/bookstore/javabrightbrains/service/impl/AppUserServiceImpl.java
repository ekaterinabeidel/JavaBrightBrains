package bookstore.javabrightbrains.service.impl;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.entity.User;
import bookstore.javabrightbrains.exception.EmailDuplicateException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.UserRepository;
import bookstore.javabrightbrains.service.AppUserService;
import bookstore.javabrightbrains.service.JwtSecurityService;
import bookstore.javabrightbrains.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtSecurityService jwtSecurityService;

    public UserDetailsService getDetailsService() {

        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessagesException.USER_NOT_FOUND));
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.USER_NOT_FOUND));
        jwtSecurityService.validateUserAccess(userId);
        if (!userDto.getEmail().equals(user.getEmail())) {
            User duplicateUser = userRepository.findByEmail(userDto.getEmail()).orElse(null);
            if (duplicateUser != null) {
                throw new EmailDuplicateException(MessagesException.DUPLICATED_EMAIL);
            }
        }

        MappingUtils.updateUserEntityFromUserDto(userDto, user);
        User updatedUser = userRepository.save(user);

        return MappingUtils.mapToUserDto(updatedUser);
    }

    public UserDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.USER_NOT_FOUND));
        jwtSecurityService.validateUserAccess(userId);
        return MappingUtils.mapToUserDto(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.USER_NOT_FOUND));
        jwtSecurityService.validateUserAccess(userId);
        userRepository.delete(user);
    }

    public User getUserById(Long userId) {
        return  userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.USER_NOT_FOUND));
    }
}

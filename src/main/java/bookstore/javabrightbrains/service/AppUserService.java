package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.entity.User;


import bookstore.javabrightbrains.exception.EmailDuplicateException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.UserRepository;
import bookstore.javabrightbrains.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AppUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Autowired
    JwtSecurityService jwtSecurityService;


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

        mappingUtils.updateUserEntityFromUserDto(userDto, user);
        User updatedUser = userRepository.save(user);

        return mappingUtils.mapToUserDto(updatedUser);
    }

    public UserDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.USER_NOT_FOUND));
        jwtSecurityService.validateUserAccess(userId);
        return mappingUtils.mapToUserDto(user);
    }

    public User getUserById(Long userId) {
        return  userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.USER_NOT_FOUND));
    }


    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.USER_NOT_FOUND));
        jwtSecurityService.validateUserAccess(userId);
        userRepository.delete(user);
    }
}

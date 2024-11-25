package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.annotation.DeleteUser;
import bookstore.javabrightbrains.annotation.GetUser;
import bookstore.javabrightbrains.annotation.UpdateUser;
import bookstore.javabrightbrains.annotation.UserControllerTag;
import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.service.AppUserService;
import bookstore.javabrightbrains.service.JwtSecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping(USER_BASE_URL)
@Validated
@RequiredArgsConstructor
@UserControllerTag
public class UserController {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private JwtSecurityService jwtSecurityService;

    @GetMapping("users/{userId}")
    @GetUser
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        jwtSecurityService.validateUserAccess(userId);
        UserDto userDto = appUserService.getUserInfo(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update/{userId}")
    @UpdateUser
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,
                                              @Valid @RequestBody UserDto userDto) {
        jwtSecurityService.validateUserAccess(userId);
        UserDto updatedUser = appUserService.updateUser(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("delete/{userId}")
    @DeleteUser
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        jwtSecurityService.validateUserAccess(userId);
        appUserService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}

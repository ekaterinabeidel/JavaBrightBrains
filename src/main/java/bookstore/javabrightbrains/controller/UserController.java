package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private AppUserService appUserService;

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto user,
            @PathVariable String id) {

        Long userId = Long.valueOf(id);
        return appUserService.updateUser(userId, user);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        Long userId = Long.valueOf(id);
        return appUserService.getUserInfo(userId);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        Long userId = Long.valueOf(id);
        return appUserService.deleteUser(userId);
    }
}

package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.repository.AppUserRepository;
import bookstore.javabrightbrains.service.AppUserService;
import bookstore.javabrightbrains.service.JwtSecurityService;
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


    @PostMapping( "/update/{id}")
    public ResponseEntity<UserDto> updateUser(
            @RequestHeader("Authorization")
            String token,
            @RequestBody UserDto user,
            @PathVariable String id) {
        Long userId = Long.valueOf(id);
        UserDto userDto =  appUserService.updateUser(userId, user, token);
        return  ResponseEntity.ok(userDto);
    }

}

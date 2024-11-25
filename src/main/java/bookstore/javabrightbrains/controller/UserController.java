package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.service.AppUserService;
import bookstore.javabrightbrains.service.JwtSecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "User Controller",
        description = "APIs for managing user-related operations, " +
                "such as updating user details, retrieving user information, and deleting users"
)
public class UserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping("users/{userId}")
    @Operation(
            summary = "Retrieve user details",
            description = "Fetches the details of a user identified by their ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userDto = appUserService.getUserInfo(userId);
        jwtSecurityService.validateUserAccess(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update/{userId}")
    @Operation(
            summary = "Update user details",
            description = "Updates the details of a user identified by their ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = appUserService.updateUser(userId, userDto);
        jwtSecurityService.validateUserAccess(userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("delete/{userId}")
    @Operation(
            summary = "Delete a user",
            description = "Deletes a user identified by their ID from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        appUserService.deleteUser(userId);
        jwtSecurityService.validateUserAccess(userId);
        return ResponseEntity.noContent().build();
    }
}

package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(
        name = "User Controller",
        description = "APIs for managing user-related operations, such as updating user details, retrieving user information, " +
                "and deleting users"
)
public class UserController {
    @Autowired
    private AppUserService appUserService;

    @PutMapping("/update/{id}")
    @Operation(
            summary = "Update user details",
            description = "Updates the details of a user identified by their ID. The updated user information is provided in the request body."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto user,
            @PathVariable String id) {

        Long userId = Long.valueOf(id);
        return appUserService.updateUser(userId, user);
    }

    @GetMapping("users/{id}")
    @Operation(
            summary = "Retrieve user details",
            description = "Fetches the details of a user identified by their ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        Long userId = Long.valueOf(id);
        return appUserService.getUserInfo(userId);
    }

    @DeleteMapping("users/{id}")
    @Operation(
            summary = "Delete a user",
            description = "Deletes a user identified by their ID from the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        Long userId = Long.valueOf(id);
        return appUserService.deleteUser(userId);
    }
}

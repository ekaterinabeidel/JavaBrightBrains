package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping(USER_BASE_URL)
@RequiredArgsConstructor
@Tag(
        name = "User Controller",
        description = "APIs for managing user-related operations, such as updating user details, retrieving user information, " +
                "and deleting users"
)
public class UserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping("users/{id}")
    @Operation(
            summary = "Retrieve user details",
            description = "Fetches the details of a user identified by their ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto userDto = appUserService.getUserInfo(id);
        return ResponseEntity.ok(userDto);
    }

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
            @Valid @PathVariable Long id,
            @Valid @RequestBody UserDto userDto) {

        UserDto updatedUser = appUserService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("delete/{id}")
    @Operation(
            summary = "Delete a user",
            description = "Deletes a user identified by their ID from the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

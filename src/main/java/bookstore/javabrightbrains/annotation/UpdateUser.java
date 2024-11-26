package bookstore.javabrightbrains.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Update user details",
        description = "Updates the details of a user identified by their ID"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid user data provided"),
        @ApiResponse(responseCode = "401", description = "Unauthorized: authentication is required"),
        @ApiResponse(responseCode = "403", description = "Forbidden: insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "409", description = "Email already exists")
})
public @interface UpdateUser {
}

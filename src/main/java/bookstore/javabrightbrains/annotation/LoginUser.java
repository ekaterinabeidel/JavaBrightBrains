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
@Operation(summary = "Authenticate a user",
        description = "Authenticate a user by validating the provided email and password")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully authenticated"),
        @ApiResponse(responseCode = "400", description = "Invalid email or password format"),
        @ApiResponse(responseCode = "401", description = "Authentication failed: invalid credentials"),
        @ApiResponse(responseCode = "403", description = "Access denied: user does not have permissions")
})
public @interface LoginUser {
}

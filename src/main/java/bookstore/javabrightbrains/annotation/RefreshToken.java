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
        summary = "Refresh the access token",
        description = "Refresh the access token by providing a valid refresh token")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Access token successfully refreshed"),
        @ApiResponse(responseCode = "400", description = "Invalid refresh token format"),
        @ApiResponse(responseCode = "401", description = "Refresh token is expired or invalid"),
        @ApiResponse(responseCode = "403", description = "Access denied")
})
public @interface RefreshToken {
}

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

@Operation(summary = "Delete book from favorites")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Favorite is successfully deleted"),
        @ApiResponse(responseCode = "401", description = "Unauthorized: authentication is required"),
        @ApiResponse(responseCode = "403", description = "Forbidden: insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "User, Book or Favorite or Book not found")
})
public @interface DeleteFavorite {
}

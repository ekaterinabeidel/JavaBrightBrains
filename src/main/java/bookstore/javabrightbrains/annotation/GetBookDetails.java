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
@Operation(summary = "Get book details", description = "Retrieve details of a specific book by its ID")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book is successfully returned"),
        @ApiResponse(responseCode = "404", description = "Book is not found")
})
public @interface GetBookDetails {
}

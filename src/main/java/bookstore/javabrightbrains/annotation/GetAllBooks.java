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
        summary = "Get books with filter, sort and pagination",
        description = "Retrieve a list of books"
)@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book is successfully showed"),
        @ApiResponse(responseCode = "400", description = "Invalid data in the request"),
        @ApiResponse(responseCode = "404", description = "Category is not found")
})
public @interface GetAllBooks {
}

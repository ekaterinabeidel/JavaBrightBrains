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
@Operation(summary = "Update an item in the cart", description = "Update the quantity of book in the cart")
@ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid data in the request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized: authentication is required"),
        @ApiResponse(responseCode = "403", description = "Forbidden: insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "404", description = "Item not found in the cart"),
        @ApiResponse(responseCode = "404", description = "Item does not belong to the user")
})
public @interface UpdateCartItem {
}

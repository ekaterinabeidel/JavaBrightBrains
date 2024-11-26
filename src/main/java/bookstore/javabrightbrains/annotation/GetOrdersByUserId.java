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
        summary = "Get orders by user ID",
        description = "Fetches a list of orders placed by the user with the specified user ID."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "204", description = "No orders found for the user"),
        @ApiResponse(responseCode = "404", description = "User not found")
})
public @interface GetOrdersByUserId {
}

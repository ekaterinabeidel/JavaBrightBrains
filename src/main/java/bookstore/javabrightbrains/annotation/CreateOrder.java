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
        summary = "Create a new order",
        description = "Creates a new order based on the provided order details in the request body. " +
                "Requires a valid cart ID, delivery information, and other order details."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid order details provided"),
        @ApiResponse(responseCode = "401", description = "Unauthorized: authentication is required"),
        @ApiResponse(responseCode = "403", description = "Forbidden: insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "Cart not found")
})
public @interface CreateOrder {
}

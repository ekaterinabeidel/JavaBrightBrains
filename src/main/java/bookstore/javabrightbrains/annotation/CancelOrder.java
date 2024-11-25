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
        summary = "Cancel an order",
        description = "Cancels an order identified by its ID. " +
                "The order can only be canceled if its status is 'Pending Payment' or 'Created'."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order canceled successfully"),
        @ApiResponse(responseCode = "400", description = "Order cannot be canceled due to its current status"),
        @ApiResponse(responseCode = "401", description = "Unauthorized: authentication is required"),
        @ApiResponse(responseCode = "403", description = "Forbidden: insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "Order not found")
})
public @interface CancelOrder {
}

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
@Operation(summary = "Get purchase history by user ID",
        description = "Get the purchase history for a specific user by their ID")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase history retrieved successfully"),
        @ApiResponse(responseCode = "204", description = "No purchase history found for the user"),
        @ApiResponse(responseCode = "401", description = "Unauthorized: authentication is required"),
        @ApiResponse(responseCode = "403", description = "Forbidden: insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "User not found")
})
public @interface GetPurchaseHistory {
}

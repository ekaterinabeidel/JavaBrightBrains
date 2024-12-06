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
@Operation(summary = "Get daily product", description = "Retrieve the product with the highest discount. " +
        "If multiple products have the same discount, a random one is selected.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book is successfully returned")
})
public @interface GetDailyProduct {
}

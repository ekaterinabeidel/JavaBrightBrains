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

@Operation(summary = "Report of revenue", description ="Get report of revenue for the period and grouping by hour, day, week, month, year"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Report created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid params provided")
})
public @interface GetRevenue {
}

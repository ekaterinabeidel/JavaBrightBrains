package bookstore.javabrightbrains.dto.order;

import bookstore.javabrightbrains.dto.book.BookOrderShortResponseDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

        @NotNull(message = "Item ID cannot be null")
        private Long id;

        @NotNull(message = "Book information is required")
        private BookOrderShortResponseDto bookOrderShortResponseDto;

        @Min(value = 1, message = "Quantity must be at least 1")
        private int quantity;

        @Min(value = 0, message = "Price cannot be negative")
        private double priceAtPurchase;

}

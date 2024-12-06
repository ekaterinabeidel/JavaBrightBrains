package bookstore.javabrightbrains.dto.cart;

import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {
    private Long cartItemId;
    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be a positive number")
    private BookShortResponseDto bookShortResponseDto;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}

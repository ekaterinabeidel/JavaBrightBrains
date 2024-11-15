package bookstore.javabrightbrains.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookRequestDto {

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @NotNull(message = "Author cannot be null")
    @Size(min = 1, max = 255, message = "Author must be between 1 and 255 characters")
    private String author;

    private String description;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private BigDecimal price;

    @Min(value = 0, message = "Discount must be greater than or equal to 0")
    private int discount;

    @NotNull(message = "CategoryId cannot be null")
    private Long categoryId;

    @Min(value = 0, message = "Total stock must be greater than or equal to 0")
    private int totalStock;

    private String imageLink;

}

package bookstore.javabrightbrains.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String description;
    private BigDecimal price;
    private int discount;
    private Long categoryId;
    private int totalStock;
    private String imageLink;
    private BigDecimal priceDiscount;
}

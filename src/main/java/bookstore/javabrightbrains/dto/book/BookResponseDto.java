package bookstore.javabrightbrains.dto.book;

import lombok.Data;
import java.math.BigDecimal;

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

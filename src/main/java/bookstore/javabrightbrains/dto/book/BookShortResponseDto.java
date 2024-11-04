package bookstore.javabrightbrains.dto.book;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookShortResponseDto {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private int discount;
    private Long categoryId;
    private int totalStock;
    private String imageLink;
    private BigDecimal priceDiscount;
}

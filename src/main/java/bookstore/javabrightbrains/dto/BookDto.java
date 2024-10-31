package bookstore.javabrightbrains.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookDto {
    private String title;
    private String author;
    private String description;
    private BigDecimal price;
    private int discount;
    private Long categoryId;
    private int totalStock;
    private String imageLink;
}

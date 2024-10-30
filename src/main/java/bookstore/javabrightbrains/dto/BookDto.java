package bookstore.javabrightbrains.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String description;
    private BigDecimal price;
    private int discount;
    private CategoryDto category;
    private int totalStock;
    private String imageLink;
}

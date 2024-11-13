package bookstore.javabrightbrains.dto.book;

import bookstore.javabrightbrains.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookFilterDto {
    private Category category;
    private Integer minPrice;
    private Integer maxPrice;
    private boolean isDiscount;
}

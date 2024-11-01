package bookstore.javabrightbrains.dto.book;

import lombok.Data;

@Data
public class BookShortResponseDto {
    private Long id;
    private String title;
    private String author;
}

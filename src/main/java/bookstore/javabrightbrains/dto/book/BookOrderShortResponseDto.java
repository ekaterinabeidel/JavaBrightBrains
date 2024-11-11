package bookstore.javabrightbrains.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderShortResponseDto {

    private Long bookId;
    private String title;
    private String imageLink;

}

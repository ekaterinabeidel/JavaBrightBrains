package bookstore.javabrightbrains.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto<T>{
    private List<T> content;
    private int page;
    private Long total;
}

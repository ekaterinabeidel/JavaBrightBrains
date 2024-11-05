package bookstore.javabrightbrains.dto.favorite;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequestDto {
    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "Book id cannot be null")
    private Long bookId;
}

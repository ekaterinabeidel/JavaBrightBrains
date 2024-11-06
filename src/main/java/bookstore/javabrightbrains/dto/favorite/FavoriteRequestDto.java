package bookstore.javabrightbrains.dto.favorite;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequestDto {
    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "Book id cannot be null")
    private Long bookId;
}

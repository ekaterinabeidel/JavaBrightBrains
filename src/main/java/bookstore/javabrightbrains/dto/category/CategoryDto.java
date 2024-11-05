package bookstore.javabrightbrains.dto.category;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class CategoryDto {
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;
}

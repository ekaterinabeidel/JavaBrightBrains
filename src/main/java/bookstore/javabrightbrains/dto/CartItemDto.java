package bookstore.javabrightbrains.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    // Нужна ли в дто id корзины?
    //В CartItemDto поле Cart обычно не включается, так как DTO обычно содержит только те данные,
    // которые необходимы для обмена информацией между клиентом и сервером.
    // Сведения о корзине (Cart) уже известны серверу,
    // так как клиент передает идентификатор пользователя,
    // а сервер определяет корзину на основе этого идентификатора.
    // Если вы нужно, чтобы клиент явно указывал корзину,
    // или если  несколько корзин на одного пользователя,
    // тогда поле Cart (или cartId) имеет смысл в CartItemDto.
    @NotNull(message = "Cart ID is required")
    private Long cartId;
}

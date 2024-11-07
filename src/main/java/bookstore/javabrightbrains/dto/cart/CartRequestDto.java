package bookstore.javabrightbrains.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {
    private Long cartId;
    private Long userId;
    private List<CartItemRequestDto> items;
}

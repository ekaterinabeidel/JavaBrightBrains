package bookstore.javabrightbrains.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Long cartId;
    private Long userId;
    private List<CartItemResponseDto> items;
}

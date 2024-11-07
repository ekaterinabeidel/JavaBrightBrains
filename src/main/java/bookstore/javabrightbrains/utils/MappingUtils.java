package bookstore.javabrightbrains.utils;

import bookstore.javabrightbrains.dto.cart.CartItemResponseDto;
import bookstore.javabrightbrains.dto.cart.CartItemRequestDto;
import bookstore.javabrightbrains.dto.cart.CartResponseDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Cart;
import bookstore.javabrightbrains.entity.CartItem;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MappingUtils {

    public CartItem toCartItem(CartItemRequestDto cartItemRequestDto, Cart cart, Book book) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setBook(book);
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        cartItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        cartItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return cartItem;
    }

    public CartResponseDto toCartResponseDto(Cart cart, List<CartItem> cartItems) {
        List<CartItemResponseDto> cartItemDtos = cartItems.stream()
                .map(this::toCartItemResponseDto)
                .collect(Collectors.toList());

        return new CartResponseDto(cart.getId(), cart.getUser().getId(), cartItemDtos);
    }

    private CartItemResponseDto toCartItemResponseDto(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getBook().getId(),
                cartItem.getQuantity()
        );
    }

}

package bookstore.javabrightbrains.utils;

import bookstore.javabrightbrains.dto.cart.CartDto;
import bookstore.javabrightbrains.dto.cart.CartItemDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Cart;
import bookstore.javabrightbrains.entity.CartItem;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MappingUtils {

    public CartItem toCartItem(CartItemDto cartItemDto, Cart cart, Book book) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setBook(book);
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        cartItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return cartItem;
    }

    public CartDto toCartDto(Cart cart) {
            return new CartDto(cart.getId(), cart.getUser().getId(), null);
        }


}

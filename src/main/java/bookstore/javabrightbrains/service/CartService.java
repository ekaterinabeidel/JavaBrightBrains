package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.cart.CartItemUpdateRequestDto;
import bookstore.javabrightbrains.dto.cart.CartItemRequestDto;
import bookstore.javabrightbrains.dto.cart.CartResponseDto;

public interface CartService {
    void addToCart(Long userId, CartItemRequestDto cartItemRequestDto);
    CartResponseDto getCart(Long userId);
    void updateCartItem(Long userId, Long cartItemId, CartItemUpdateRequestDto cartItemUpdateRequestDto);
    void deleteCartItem(Long userId, Long cartItemId);
}

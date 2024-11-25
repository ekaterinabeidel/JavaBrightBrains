package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.annotation.*;
import bookstore.javabrightbrains.dto.cart.CartItemUpdateRequestDto;
import bookstore.javabrightbrains.dto.cart.CartItemRequestDto;
import bookstore.javabrightbrains.dto.cart.CartResponseDto;
import bookstore.javabrightbrains.service.CartService;
import bookstore.javabrightbrains.service.JwtSecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;
@Validated
@RestController
@RequestMapping( USER_BASE_URL + "/{userId}/cart")
@RequiredArgsConstructor
@CartControllerTag
public class CartController {
    @Autowired
    private  CartService cartService;
    @Autowired
    private JwtSecurityService jwtSecurityService;

    @GetMapping
    @GetCart
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long userId) {
        jwtSecurityService.validateUserAccess(userId);
        CartResponseDto cartResponseDto = cartService.getCart(userId);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PostMapping("/items")
    @AddToCart
    public ResponseEntity<String> addToCart(@PathVariable Long userId,
                                            @Valid @RequestBody CartItemRequestDto cartItemRequestDto) {
        jwtSecurityService.validateUserAccess(userId);
        cartService.addToCart(userId, cartItemRequestDto);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/items/{cartItemId}")
    @UpdateCartItem
    public ResponseEntity<String> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId,
                                                 @Valid @RequestBody CartItemUpdateRequestDto cartItemUpdateRequestDto) {
        jwtSecurityService.validateUserAccess(userId);
        cartService.updateCartItem(userId, cartItemId, cartItemUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{cartItemId}")
    @DeleteCartItem
    public ResponseEntity<String> deleteCartItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        jwtSecurityService.validateUserAccess(userId);
        cartService.deleteCartItem(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
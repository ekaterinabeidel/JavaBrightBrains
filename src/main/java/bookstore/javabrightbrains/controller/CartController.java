package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.cart.CartItemDto;
import bookstore.javabrightbrains.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addToCart(@Valid @RequestBody CartItemDto cartItemDto) {
        cartService.addToCart(cartItemDto);
        return ResponseEntity.ok("Item added to cart");
    }
}

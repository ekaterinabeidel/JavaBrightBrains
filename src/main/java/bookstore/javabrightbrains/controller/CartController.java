package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.cart.CartDto;
import bookstore.javabrightbrains.dto.cart.CartItemDto;
import bookstore.javabrightbrains.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping( USER_BASE_URL + "/{userId}/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        CartDto cartDto = cartService.getCart(userId);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/items")
    @Operation(summary = "Add an item to the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item successfully added to the cart"),
            @ApiResponse(responseCode = "400", description = "Invalid data in the request"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<String> addToCart(@PathVariable Long userId, @Valid @RequestBody CartItemDto cartItemDto) {
        cartService.addToCart(userId, cartItemDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update an item in the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data in the request"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "404", description = "Item not found in the cart"),
            @ApiResponse(responseCode = "404", description = "Item does not belong to the user")
    })
    public ResponseEntity<String> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId,
                                                 @Valid @RequestBody CartItemDto cartItemDto) {
        cartService.updateCartItem(userId, cartItemId, cartItemDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Delete an item from the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item successfully deleted from the cart"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "404", description = "Item not found in the cart"),
            @ApiResponse(responseCode = "404", description = "Item does not belong to the user")
    })
    public ResponseEntity<String> deleteCartItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        cartService.deleteCartItem(userId, cartItemId);
        return ResponseEntity.ok().build();
    }

}
package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.cart.CartItemUpdateRequestDto;
import bookstore.javabrightbrains.dto.cart.CartItemRequestDto;
import bookstore.javabrightbrains.dto.cart.CartResponseDto;
import bookstore.javabrightbrains.service.CartService;
import bookstore.javabrightbrains.service.JwtSecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Cart Controller", description = "APIs for managing manages the user's shopping cart")
public class CartController {
    @Autowired
    private  CartService cartService;
    @Autowired
    private JwtSecurityService jwtSecurityService;

    @GetMapping
    @Operation(summary = "Get the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long userId) {
        jwtSecurityService.validateUserAccess(userId);
        CartResponseDto cartResponseDto = cartService.getCart(userId);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PostMapping("/items")
    @Operation(summary = "Add an item to the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item successfully added to the cart"),
            @ApiResponse(responseCode = "400", description = "Invalid data in the request"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<String> addToCart(@PathVariable Long userId, @Valid @RequestBody CartItemRequestDto cartItemRequestDto) {
        jwtSecurityService.validateUserAccess(userId);
        cartService.addToCart(userId, cartItemRequestDto);
        return ResponseEntity.status(201).build();
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
                                                 @Valid @RequestBody CartItemUpdateRequestDto cartItemUpdateRequestDto) {
        jwtSecurityService.validateUserAccess(userId);
        cartService.updateCartItem(userId, cartItemId, cartItemUpdateRequestDto);
        return ResponseEntity.noContent().build();
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
        jwtSecurityService.validateUserAccess(userId);
        cartService.deleteCartItem(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }

}
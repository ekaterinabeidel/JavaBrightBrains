package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.order.OrderRequestDto;
import bookstore.javabrightbrains.dto.order.OrderResponseDto;
import bookstore.javabrightbrains.dto.order.OrderShortResponseDto;
import bookstore.javabrightbrains.dto.order.PurchaseHistoryDto;
import bookstore.javabrightbrains.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping(USER_BASE_URL + "/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto createdOrder = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(201).body(createdOrder);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.status(200).body(order);
    }

    @GetMapping("/get-orders/{userId}")
    public ResponseEntity<List<OrderShortResponseDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderShortResponseDto> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(orders);
        }
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderShortResponseDto> cancelOrder(@PathVariable Long orderId) {
        OrderShortResponseDto order = orderService.cancelOrder(orderId);
        return ResponseEntity.status(200).body(order);
    }

    @GetMapping("/history")
    @Operation(summary = "Get purchase history by user ID", description = "Get the purchase history for a specific user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchase history retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No purchase history found for the user"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<PurchaseHistoryDto>> getPurchaseHistory(
            @Parameter(description = "User ID to fetch purchase history for", required = true) @RequestParam Long userId) {
        List<PurchaseHistoryDto> purchaseHistory = orderService.getPurchaseHistory(userId);
        if (purchaseHistory.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(purchaseHistory);
        }
    }
}
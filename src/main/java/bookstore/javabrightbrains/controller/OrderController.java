package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.order.OrderRequestDto;
import bookstore.javabrightbrains.dto.order.OrderResponseDto;
import bookstore.javabrightbrains.dto.order.OrderShortResponseDto;
import bookstore.javabrightbrains.dto.order.PurchaseHistoryDto;
import bookstore.javabrightbrains.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping(USER_BASE_URL + "/orders")
@Tag(name = "Order Controller",
        description = "APIs for managing  orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order based on the provided order details in the request body. Requires a valid cart ID, delivery information, and other order details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order details provided"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto createdOrder = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(201).body(createdOrder);
    }

    @Operation(
            summary = "Get order details by ID",
            description = "Fetches the details of an order based on the provided order ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.status(200).body(order);
    }

    @Operation(
            summary = "Get orders by user ID",
            description = "Fetches a list of orders placed by the user with the specified user ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No orders found for the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/get-orders/{userId}")
    public ResponseEntity<List<OrderShortResponseDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderShortResponseDto> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(orders);
        }
    }

    @Operation(
            summary = "Cancel an order",
            description = "Cancels an order identified by its ID. The order can only be canceled if its status is 'Pending Payment' or 'Created'."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order canceled successfully"),
            @ApiResponse(responseCode = "400", description = "Order cannot be canceled due to its current status"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
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

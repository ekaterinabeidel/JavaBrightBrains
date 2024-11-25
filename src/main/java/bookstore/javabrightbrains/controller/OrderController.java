package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.annotation.*;
import bookstore.javabrightbrains.dto.order.OrderRequestDto;
import bookstore.javabrightbrains.dto.order.OrderResponseDto;
import bookstore.javabrightbrains.dto.order.OrderShortResponseDto;
import bookstore.javabrightbrains.dto.order.PurchaseHistoryDto;
import bookstore.javabrightbrains.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping(USER_BASE_URL + "/orders")
@OrderControllerTag
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    @CreateOrder
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto createdOrder = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(201).body(createdOrder);
    }

    @GetMapping("/get/{id}")
    @GetOrderById
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/get-orders/{userId}")
    @GetOrdersByUserId
    public ResponseEntity<List<OrderShortResponseDto>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderShortResponseDto> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.ok().body(orders);
        }
    }

    @PutMapping("/update/{orderId}")
    @CancelOrder
    public ResponseEntity<OrderShortResponseDto> cancelOrder(@PathVariable Long orderId) {
        OrderShortResponseDto order = orderService.cancelOrder(orderId);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/history/{userId}")
    @GetPurchaseHistory
    public ResponseEntity<List<PurchaseHistoryDto>> getPurchaseHistory(
            @Parameter(description = "User ID to fetch purchase history for", required = true) @PathVariable Long userId) {
        List<PurchaseHistoryDto> purchaseHistory = orderService.getPurchaseHistory(userId);
        if (purchaseHistory.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(purchaseHistory);
        }
    }
}

package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.order.OrderRequestDto;
import bookstore.javabrightbrains.dto.order.OrderResponseDto;
import bookstore.javabrightbrains.dto.order.OrderShortResponseDto;
import bookstore.javabrightbrains.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}

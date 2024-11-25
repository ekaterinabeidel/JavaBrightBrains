package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.order.OrderRequestDto;
import bookstore.javabrightbrains.dto.order.OrderResponseDto;
import bookstore.javabrightbrains.dto.order.OrderShortResponseDto;
import bookstore.javabrightbrains.dto.order.PurchaseHistoryDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto getOrderById(Long id);
    List<OrderShortResponseDto> getOrdersByUserId(Long userId);
    OrderShortResponseDto cancelOrder(Long orderId);
    List<PurchaseHistoryDto> getPurchaseHistory(Long userId);
}

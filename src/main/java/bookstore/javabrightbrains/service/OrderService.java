package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.order.*;
import bookstore.javabrightbrains.entity.Order;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto getOrderById(Long id);

    List<OrderShortResponseDto> getOrdersByUserId(Long userId);

    OrderShortResponseDto cancelOrder(Long orderId);

    List<PurchaseHistoryDto> getPurchaseHistory(Long userId);

    List<Order> findAllOrders();

    void saveAllOrders(List<Order> orders);
}

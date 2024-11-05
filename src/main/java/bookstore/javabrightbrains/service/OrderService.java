package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.entity.Order;
import bookstore.javabrightbrains.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public Order createOrder(Order order) {
        return orderRepository.save(order);

    }
    public Order getOrderStatus(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}

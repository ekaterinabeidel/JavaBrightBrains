package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.entity.Order;
import bookstore.javabrightbrains.repository.OrderRepository;
import bookstore.javabrightbrains.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderStatusUpdater {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusUpdater.class);
    @Autowired
    private OrderRepository orderRepository;
    @Scheduled(initialDelay = 60000, fixedRate = 30000)
    @Transactional
    public void updateOrderStatuses() {

        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            if(order.getCreatedAt().toLocalDateTime().isAfter(LocalDateTime.now().minusDays(1))) {
                logger.info("Updating order " + order.getId());
                OrderStatus currentStatus = order.getStatus();
                OrderStatus nextStatus = getNextStatus(currentStatus);

                if (nextStatus != null) {
                    order.setStatus(nextStatus);
                }
            }
        }
        orderRepository.saveAll(orders);
        logger.info("Updated orders saved.");
    }
    private OrderStatus getNextStatus(OrderStatus currentStatus) {
        return switch (currentStatus) {
            case PENDING -> OrderStatus.PAID;
            case PAID -> OrderStatus.SHIPPED;
            case SHIPPED -> OrderStatus.DELIVERED;
            default -> null;
        };
    }
}

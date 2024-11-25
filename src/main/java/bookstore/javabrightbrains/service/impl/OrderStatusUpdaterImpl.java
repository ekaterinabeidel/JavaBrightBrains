package bookstore.javabrightbrains.service.impl;

import bookstore.javabrightbrains.entity.Order;
import bookstore.javabrightbrains.repository.OrderRepository;
import bookstore.javabrightbrains.service.OrderStatusUpdater;
import bookstore.javabrightbrains.utils.OrderStatus;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static bookstore.javabrightbrains.utils.OrderStatus.*;

@Service
public class OrderStatusUpdaterImpl implements OrderStatusUpdater {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusUpdaterImpl.class);
    @Autowired
    private OrderRepository orderRepository;
    @Scheduled(initialDelay = 60000, fixedRate = 30000)
    @Transactional
    public void updateOrderStatuses() {

        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            if(order.getCreatedAt().toLocalDateTime().isAfter(LocalDateTime.now().minusDays(1))) {
                logger.info("Updating order " + order.getId());
                switch (order.getStatus()) {
                    case PENDING:
                        order.setStatus(PAID);
                        break;
                    case PAID:
                        order.setStatus(SHIPPED);
                        break;
                    case SHIPPED:
                        order.setStatus(OrderStatus.DELIVERED);
                        break;
                    default:
                        break;
                }
            }
        }
        orderRepository.saveAll(orders);
        logger.info("Updated orders saved.");
    }
}

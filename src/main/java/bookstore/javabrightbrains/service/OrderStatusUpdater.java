package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.entity.Order;
import bookstore.javabrightbrains.repository.OrderRepository;
import bookstore.javabrightbrains.utils.OrderStatus;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static bookstore.javabrightbrains.utils.OrderStatus.*;

@Service
public class OrderStatusUpdater {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusUpdater.class);
    @Autowired
    private OrderRepository orderRepository;
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void updateOrderStatuses() {

        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            switch (order.getStatus()) {
                case PENDING_PAYMENT:
                    order.setStatus(PAID);
                    break;
                case PAID:
                    order.setStatus(IN_TRANSIT);
                    break;
                case IN_TRANSIT:
                    order.setStatus(OrderStatus.DELIVERED);
                    break;

                default:
                    break;
            }
        }
        orderRepository.saveAll(orders);
        logger.info("Updated orders saved.");
    }
}

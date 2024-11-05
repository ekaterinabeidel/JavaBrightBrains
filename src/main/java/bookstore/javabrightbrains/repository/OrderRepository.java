package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

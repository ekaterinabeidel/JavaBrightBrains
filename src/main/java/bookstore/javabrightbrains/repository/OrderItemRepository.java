package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.dto.book.BookNotPaidDto;
import bookstore.javabrightbrains.dto.book.TopBookDto;
import bookstore.javabrightbrains.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long id);

    @Query("SELECT b.id AS bookId, b.title AS title, SUM(oi.quantity) AS totalQuantity " +
            "FROM OrderItem oi " +
            "JOIN oi.book b " +
            "JOIN oi.order o " +
            "WHERE o.status = 'Delivered' " +
            "GROUP BY b.id, b.title " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<TopBookDto> findTopBooksByDeliveredOrders(Pageable pageable);

    @Query("SELECT b.id AS bookId, b.title AS title, COUNT(oi.quantity) AS totalQuantity " +
            "FROM OrderItem oi " +
            "JOIN oi.book b " +
            "JOIN oi.order o " +
            "WHERE o.status = 'Canceled' " +
            "GROUP BY b.id, b.title " +
            "ORDER BY COUNT(oi.quantity) DESC")
    List<TopBookDto> findTopCancelledBooks(Pageable pageable);


    @Query("SELECT b.id AS bookId, b.title AS title " +
            "FROM OrderItem oi " +
            "JOIN oi.book b " +
            "JOIN oi.order o " +
            "WHERE o.status = 'PENDING' AND o.createdAt <= :cutoffDate " +
            "GROUP BY b.id, b.title")
    List<BookNotPaidDto> findPendingBooksOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}

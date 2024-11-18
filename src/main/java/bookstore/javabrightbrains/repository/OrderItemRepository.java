package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.dto.book.BookNotPaidDto;
import bookstore.javabrightbrains.dto.book.ProfitDto;
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

    @Query(value = """
            SELECT 
                CASE 
                    WHEN :groupBy = 'hour' THEN DATE_FORMAT(o.created_at, '%Y-%m-%d %H:00:00')
                    WHEN :groupBy = 'day' THEN DATE_FORMAT(o.created_at, '%Y-%m-%d')
                    WHEN :groupBy = 'week' THEN DATE_FORMAT(o.created_at, '%x-W%v')
                    WHEN :groupBy = 'month' THEN DATE_FORMAT(o.created_at, '%Y-%m')
                    WHEN :groupBy = 'year' THEN DATE_FORMAT(o.created_at, '%Y')
                    ELSE DATE_FORMAT(o.created_at, '%Y-%m-%d') 
                END AS period,
                SUM(oi.quantity * oi.price_at_purchase) AS totalProfit
            FROM order_items oi
            JOIN orders o ON oi.order_id = o.id
            WHERE o.status = 'Delivered' 
                AND o.created_at BETWEEN :startDate AND :endDate
            GROUP BY period
            ORDER BY period ASC
            """, nativeQuery = true)
    List<ProfitDto> findProfitGroupedBy(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("groupBy") String groupBy
    );
}

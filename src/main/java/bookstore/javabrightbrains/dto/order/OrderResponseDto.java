package bookstore.javabrightbrains.dto.order;

import bookstore.javabrightbrains.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderResponseDto {
    private Long id;
    private List<OrderItemDto> items;
    private Timestamp createdAt;
    private String deliveryAddress;
    private String contactPhone;
    private String deliveryMethod;
    private OrderStatus status;
    private BigDecimal totalPrice;
}


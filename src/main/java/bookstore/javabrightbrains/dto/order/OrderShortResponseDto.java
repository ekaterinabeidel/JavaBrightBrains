package bookstore.javabrightbrains.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderShortResponseDto {
    private Long id;
    private List<OrderItemDto> items;
    private Timestamp createdAt;
    private String status;
    private BigDecimal totalPrice;
}

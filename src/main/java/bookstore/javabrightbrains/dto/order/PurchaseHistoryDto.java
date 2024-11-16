package bookstore.javabrightbrains.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseHistoryDto {
    private String title;
    private Timestamp createdAt;
    private String imageLink;
    private double price;
    private Long orderId;
}

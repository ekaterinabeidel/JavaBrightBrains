package bookstore.javabrightbrains.dto.order;

import bookstore.javabrightbrains.dto.book.BookOrderShortResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

        private Long id;
        private BookOrderShortResponseDto bookOrderShortResponseDto;
        private int quantity;
        private double priceAtPurchase;

}

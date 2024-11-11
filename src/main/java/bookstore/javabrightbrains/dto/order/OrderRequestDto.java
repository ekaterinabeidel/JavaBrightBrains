package bookstore.javabrightbrains.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderRequestDto {

    private Long cartId;
    private String deliveryAddress;
    private String contactPhone;
    private String deliveryMethod;

}
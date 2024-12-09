package bookstore.javabrightbrains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING ("PENDING"),
    PAID ("PAID"),
    SHIPPED ("SHIPPED"),
    DELIVERED ("DELIVERED"),
    CANCELED ("CANCELED");

    private final String status;

}

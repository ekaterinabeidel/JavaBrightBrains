package bookstore.javabrightbrains.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING ("PENDING"),
    PAID ("PAID"),
    SHIPPED ("SHIPPED"),
    DELIVERED ("DELIVERED"),
    CANCELED ("CANCELED"),
    PROCESSING("PROCESSING");

    private final String status;

}

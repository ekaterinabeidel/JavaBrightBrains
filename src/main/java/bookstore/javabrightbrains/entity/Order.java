package bookstore.javabrightbrains.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String deliveryAddress;
    private String contactPhone;
    private String deliveryMethod;
    private String status;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;

        if (!getId().equals(order.getId())) return false;
        if (!getDeliveryAddress().equals(order.getDeliveryAddress())) return false;
        if (!getContactPhone().equals(order.getContactPhone())) return false;
        if (!getDeliveryMethod().equals(order.getDeliveryMethod())) return false;
        return getStatus().equals(order.getStatus());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getDeliveryAddress().hashCode();
        result = 31 * result + getContactPhone().hashCode();
        result = 31 * result + getDeliveryMethod().hashCode();
        result = 31 * result + getStatus().hashCode();
        return result;
    }
}

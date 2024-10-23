package bookstore.javabrightbrains.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
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
}

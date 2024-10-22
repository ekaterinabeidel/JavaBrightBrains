package bookstore.javabrightbrains.entity;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
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

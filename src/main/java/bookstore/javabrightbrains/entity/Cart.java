package bookstore.javabrightbrains.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;

        return getId().equals(cart.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}

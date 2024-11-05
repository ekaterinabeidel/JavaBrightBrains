package bookstore.javabrightbrains.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.from(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorite favorite)) return false;

        return getId().equals(favorite.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}

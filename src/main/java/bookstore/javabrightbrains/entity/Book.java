package bookstore.javabrightbrains.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String title;
    private String author;
    private String description;
    private BigDecimal price;
    private int discount;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Favorite> favorites;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private int totalStock;
    private String imageLink;

    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Timestamp.from(Instant.now());
    }

    public Book(String title, String author, String description, BigDecimal price, int discount, Long categoryId, int totalStock, String imageLink) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.category = new Category();
        this.category.setId(categoryId);
        this.totalStock = totalStock;
        this.imageLink = imageLink;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;

        if (!getId().equals(book.getId())) return false;
        if (!getTitle().equals(book.getTitle())) return false;
        if (!getAuthor().equals(book.getAuthor())) return false;
        return getDescription().equals(book.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getAuthor().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }

}


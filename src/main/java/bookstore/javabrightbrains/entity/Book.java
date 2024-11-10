package bookstore.javabrightbrains.entity;

import bookstore.javabrightbrains.utils.Utils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Entity
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

    public void setPriceDiscount() {
        addPriceWithDiscount();
    }

    private BigDecimal price;
    private BigDecimal priceDiscount;
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

    protected void addPriceWithDiscount() {
        if(discount > 0) {
            priceDiscount = Utils.getPriceWithDiscount(price, discount);
        } else {
            priceDiscount = price;
        }
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


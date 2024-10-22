package bookstore.javabrightbrains.entity;

import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String title;
    private String author;
    private String description;
    private double price;
    private int discount;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private int total;
    private String image;
}


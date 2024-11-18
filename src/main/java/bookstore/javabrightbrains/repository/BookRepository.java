package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long>, FilterBookRepository {
    List<Book> findFirstByOrderByDiscountDesc();

    List<Book> findByDiscount(int discount);
}

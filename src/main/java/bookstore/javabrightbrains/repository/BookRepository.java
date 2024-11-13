package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long>, FilterBookRepository {
    @Query("SELECT b FROM Book b WHERE b.discount = (SELECT MAX(b2.discount) FROM Book b2)")
    List<Book> findBooksWithMaxDiscount();
}

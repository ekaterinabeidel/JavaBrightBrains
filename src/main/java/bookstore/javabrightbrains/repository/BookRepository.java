package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

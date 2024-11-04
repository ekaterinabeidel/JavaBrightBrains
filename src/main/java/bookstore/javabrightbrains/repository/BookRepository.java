package bookstore.javabrightbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bookstore.javabrightbrains.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}

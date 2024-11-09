package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.dto.book.BookFilterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import bookstore.javabrightbrains.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, FilterBookRepository {
    List<Book>findByFilter(BookFilterDto filter);
}

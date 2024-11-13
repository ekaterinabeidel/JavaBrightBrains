package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.dto.book.BookFilterDto;
import bookstore.javabrightbrains.entity.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FilterBookRepository {
    Page<Book> findByFilter(BookFilterDto filter, Pageable pageable);
}

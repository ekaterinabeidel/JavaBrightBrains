package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.book.BookRequestDto;
import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.book.PageResponseDto;
import bookstore.javabrightbrains.entity.Book;

public interface BookService {
    BookResponseDto save(BookRequestDto bookDto);
    BookResponseDto update(Long id, BookRequestDto bookDto);
    void delete(Long id);
    PageResponseDto<BookShortResponseDto> findAll(
            int pageNum,
            int pageSize,
            Long categoryId,
            Integer minPrice,
            Integer maxPrice,
            Boolean isDiscount,
            String sortBy,
            String sortDirect
    );
    BookResponseDto findById(Long id);
    BookResponseDto getDailyProduct();

    Book getBookById(Long id);
}
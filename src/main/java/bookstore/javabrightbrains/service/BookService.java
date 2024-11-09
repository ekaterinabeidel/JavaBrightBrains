package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.book.BookFilterDto;
import bookstore.javabrightbrains.dto.book.BookRequestDto;
import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Category;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.BookRepository;
import bookstore.javabrightbrains.repository.FilterBookRepository;
import bookstore.javabrightbrains.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryService categoryService;

    public BookResponseDto save(BookRequestDto bookDto) {
        Category category = categoryService.findEntityById(bookDto.getCategoryId());
        if (category == null) {
            throw new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND);
        }
        Book book = Utils.convertToBookEntity(bookDto, categoryService);
        book.setCategory(category);
        Book savedBook = bookRepository.save(book);
        return Utils.convertToBookResponseDto(savedBook);
    }

    public BookResponseDto update(Long id, BookRequestDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException(MessagesException.BOOK_NOT_FOUND));
        Category category = categoryService.findEntityById(bookDto.getCategoryId());
        if (category == null) {
            throw new IdNotFoundException(MessagesException.CATEGORY_NOT_FOUND);
        }
        book = Utils.updateBookFromDto(book, bookDto, categoryService);
        book.setCategory(category);
        Book updatedBook = bookRepository.save(book);
        return Utils.convertToBookResponseDto(updatedBook);
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException(MessagesException.BOOK_NOT_FOUND));
        bookRepository.delete(book);
    }

    public List<BookShortResponseDto> findAll(BookFilterDto filter) {
        List<Book> books = bookRepository.findByFilter(filter);
        return books.stream().map(Utils::convertToBookShortResponseDto).collect(Collectors.toList());
    }

    public BookResponseDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException(MessagesException.BOOK_NOT_FOUND));
        return Utils.convertToBookResponseDto(book);
    }

}

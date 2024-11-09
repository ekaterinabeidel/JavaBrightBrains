package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookFilterDto;
import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.entity.Category;
import bookstore.javabrightbrains.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookShortResponseDto>> getAllBooks() {
        Category category =new Category();
        category.setId(1l);
        category.setName("Science Fiction");
        BookFilterDto filter = new BookFilterDto(
                category,
                5,
                14,
                false
        );
        List<BookShortResponseDto> books = bookService.findAll(filter);
        return ResponseEntity.ok(books);
    }

    @GetMapping(USER_BASE_URL + "/books/{bookId}")
    public ResponseEntity<BookResponseDto> getBookDetail(@PathVariable Long bookId) {
        BookResponseDto bookDto = bookService.findById(bookId);
        return ResponseEntity.ok(bookDto);
    }
}

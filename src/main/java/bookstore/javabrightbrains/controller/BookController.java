package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping("/api")
@Tag(name = "Book Controller", description = "APIs for managing books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    public ResponseEntity<List<BookShortResponseDto>> getAllBooks() {
        List<BookShortResponseDto> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping(USER_BASE_URL + "/books/{bookId}")
    @Operation(summary = "Get book details", description = "Retrieve details of a specific book by its ID")
    public ResponseEntity<BookResponseDto> getBookDetail(@PathVariable Long bookId) {
        BookResponseDto bookDto = bookService.findById(bookId);
        return ResponseEntity.ok(bookDto);
    }
}

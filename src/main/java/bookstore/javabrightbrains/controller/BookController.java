package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.BookDto;
import bookstore.javabrightbrains.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;
import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/admin/books")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        BookDto createdBookDto = bookService.save(bookDto);
        return ResponseEntity.status(201).body(createdBookDto);
    }

    @PutMapping("/admin/books/{bookId}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long bookId, @RequestBody BookDto bookDto) {
        BookDto updatedBookDto = bookService.update(bookId, bookDto);
        return ResponseEntity.ok(updatedBookDto);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/admin/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.delete(bookId);
        return ResponseEntity.ok().build();
    }
}

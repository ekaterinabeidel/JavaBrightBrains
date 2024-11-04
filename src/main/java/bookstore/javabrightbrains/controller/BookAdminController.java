package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookRequestDto;
import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;

@RestController
@RequestMapping(ADMIN_BASE_URL + "/books")
public class BookAdminController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody BookRequestDto bookDto) {
        BookResponseDto createdBookDto = bookService.save(bookDto);
        return ResponseEntity.status(201).body(createdBookDto);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookRequestDto bookDto) {
        BookResponseDto updatedBookDto = bookService.update(bookId, bookDto);
        return ResponseEntity.ok(updatedBookDto);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.delete(bookId);
        return ResponseEntity.ok().build();
    }
}

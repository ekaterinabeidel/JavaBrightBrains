package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookRequestDto;
import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;

@RestController
@RequestMapping(ADMIN_BASE_URL + "/books")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Book Admin Controller", description = "APIs for managing books by administrators")
public class BookAdminController {
    @Autowired
    private BookService bookService;

    @PostMapping
    @Operation(summary = "Add a new book", description = "Create a new book with the provided details")
    public ResponseEntity<BookResponseDto> addBook(@Valid @RequestBody BookRequestDto bookDto) {
        BookResponseDto createdBookDto = bookService.save(bookDto);
        return ResponseEntity.status(201).body(createdBookDto);
    }

    @PutMapping("/{bookId}")
    @Operation(summary = "Update a book", description = "Update the details of an existing book by its ID")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookRequestDto bookDto) {
        BookResponseDto updatedBookDto = bookService.update(bookId, bookDto);
        return ResponseEntity.ok(updatedBookDto);
    }

    @DeleteMapping("/{bookId}")
    @Operation(summary = "Delete a book", description = "Delete a book by its ID")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.delete(bookId);
        return ResponseEntity.ok().build();
    }
}

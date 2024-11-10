package bookstore.javabrightbrains.controller;


import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.book.PageResponseDto;
import bookstore.javabrightbrains.service.BookService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books/pageNumber/{pageNum}/pageSize/{pageSize}/" +
            "categoryId/{categoryId}/minPrice/{minPrice}/" +
            "maxPrice/{maxPrice}/isDiscount/{isDiscount}")
    public ResponseEntity<PageResponseDto<BookShortResponseDto>> getAllBooks(
            @PathVariable @DefaultValue("0") int pageNum,
            @PathVariable @DefaultValue("5") int pageSize,
            @Nullable
            @PathVariable Long categoryId,
            @Nullable
            @PathVariable Integer minPrice,
            @Nullable
            @PathVariable Integer maxPrice,
            @PathVariable @DefaultValue("false") boolean isDiscount

    ) {

        PageResponseDto<BookShortResponseDto> books = bookService.findAll(
                pageNum,
                pageSize,
                categoryId,
                minPrice,
                maxPrice,
                isDiscount);
        return ResponseEntity.ok(books);
    }

    @GetMapping(USER_BASE_URL + "/books/{bookId}")
    public ResponseEntity<BookResponseDto> getBookDetail(@PathVariable Long bookId) {
        BookResponseDto bookDto = bookService.findById(bookId);
        return ResponseEntity.ok(bookDto);
    }
}

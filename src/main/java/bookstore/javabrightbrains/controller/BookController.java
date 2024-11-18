package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.book.PageResponseDto;
import bookstore.javabrightbrains.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static bookstore.javabrightbrains.utils.Constants.PUBLIC_BASE_URL;


@RestController
@RequestMapping(PUBLIC_BASE_URL)
@Tag(name = "Book Controller", description = "APIs for managing books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books/pageNumber/{pageNum}/pageSize/{pageSize}")
    @Operation(
            summary = "Get books with filter, sort and pagination",
            description = "Retrieve a list of books"
    )
    public ResponseEntity<PageResponseDto<BookShortResponseDto>> getAllBooks(
            @PathVariable
            @Min(1)
            @Parameter(description = "Page number from 1")
            Integer pageNum,
            @PathVariable Integer pageSize,
            @Nullable
            @RequestParam(value = "sortBy", required = false)
            @PathVariable String sortBy,
            @Nullable
            @Parameter(description = " 'asc' or 'desc' ")
            @RequestParam(value = "sortDirect", required = false)
            @PathVariable String sortDirect,
            @Nullable
            @RequestParam(value = "categoryId", required = false)
            @PathVariable Integer categoryId,
            @Nullable
            @RequestParam(value = "minPrice", required = false)
            @PathVariable Integer minPrice,
            @Nullable
            @RequestParam(value = "maxPrice", required = false)
            @PathVariable Integer maxPrice,
            @RequestParam(value = "isDiscount", required = false)
            @PathVariable boolean isDiscount


    ) {

        Long categoryIdLong = null;
        if (categoryId != null) {
            categoryIdLong = Long.valueOf(categoryId);
        }

        PageResponseDto<BookShortResponseDto> books = bookService.findAll(
                pageNum,
                pageSize,
                categoryIdLong,
                minPrice,
                maxPrice,
                isDiscount,
                sortBy,
                sortDirect);

        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{bookId}")
    @Operation(summary = "Get book details", description = "Retrieve details of a specific book by its ID")
    public ResponseEntity<BookResponseDto> getBookDetail(@PathVariable Long bookId) {
        BookResponseDto bookDto = bookService.findById(bookId);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/daily-product")
    @Operation(summary = "Get daily product", description = "Retrieve the product with the highest discount. If multiple products have the same discount, a random one is selected.")
    public ResponseEntity<BookResponseDto> getDailyProduct() {
        BookResponseDto dailyProduct = bookService.getDailyProduct();
        if (dailyProduct == null) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(dailyProduct);
    }

}
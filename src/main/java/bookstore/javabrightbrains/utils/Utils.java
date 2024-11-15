package bookstore.javabrightbrains.utils;

import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.dto.book.BookRequestDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Category;
import bookstore.javabrightbrains.service.CategoryService;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    public static Book convertToBookEntity(BookRequestDto bookRequestDto, CategoryService categoryService) {
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setDescription(bookRequestDto.getDescription());
        book.setPrice(bookRequestDto.getPrice());
        book.setDiscount(bookRequestDto.getDiscount());

        if (bookRequestDto.getDiscount() == 0) {
            book.setPriceDiscount(bookRequestDto.getPrice());
        } else {
            book.setPriceDiscount(Utils.getPriceWithDiscount(bookRequestDto.getPrice(), bookRequestDto.getDiscount()));
        }

        book.setTotalStock(bookRequestDto.getTotalStock());
        book.setImageLink(bookRequestDto.getImageLink());

        Long categoryId = bookRequestDto.getCategoryId();
        if (categoryId != null) {
            Category category = categoryService.findEntityById(categoryId);
            book.setCategory(category);
        }
        return book;
    }

    public static BigDecimal getPriceWithDiscount(BigDecimal price, int discount) {
        BigDecimal discountAmount = price
                .multiply(BigDecimal.valueOf(discount))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        return price.subtract(discountAmount);
    }

    public static BookResponseDto convertToBookResponseDto(Book book) {
        BookResponseDto bookDto = new BookResponseDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setDescription(book.getDescription());
        bookDto.setPrice(book.getPrice());
        bookDto.setDiscount(book.getDiscount());
        bookDto.setTotalStock(book.getTotalStock());
        bookDto.setImageLink(book.getImageLink());
        bookDto.setPriceDiscount(book.getPriceDiscount());

        Category category = book.getCategory();

        if (category != null) {
            bookDto.setCategoryId(category.getId());
        }

        return bookDto;
    }

    public static BookShortResponseDto convertToBookShortResponseDto(Book book) {
        BookShortResponseDto bookDto = new BookShortResponseDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setDiscount(book.getDiscount());
        bookDto.setTotalStock(book.getTotalStock());
        bookDto.setImageLink(book.getImageLink());
        bookDto.setPriceDiscount(book.getPriceDiscount());

        Category category = book.getCategory();
        if (category != null) {
            bookDto.setCategoryId(category.getId());
        }

        return bookDto;
    }

    public static Book updateBookFromDto(Book book, BookRequestDto bookDto, CategoryService categoryService) {
        if (bookDto.getTitle() != null) book.setTitle(bookDto.getTitle());
        if (bookDto.getAuthor() != null) book.setAuthor(bookDto.getAuthor());
        if (bookDto.getDescription() != null) book.setDescription(bookDto.getDescription());
        if (bookDto.getPrice() != null) book.setPrice(bookDto.getPrice());
        if (bookDto.getDiscount() != 0) book.setDiscount(bookDto.getDiscount());
        if (bookDto.getDiscount() > 0) {
            book.setPriceDiscount(getPriceWithDiscount(bookDto.getPrice(), bookDto.getDiscount()));
        } else if (bookDto.getDiscount() == 0) {
            book.setPriceDiscount(bookDto.getPrice());
        }
        if (bookDto.getTotalStock() != 0) book.setTotalStock(bookDto.getTotalStock());
        if (bookDto.getImageLink() != null) book.setImageLink(bookDto.getImageLink());

        Long categoryId = bookDto.getCategoryId();
        if (categoryId != null) {
            Category category = categoryService.findEntityById(categoryId);
            book.setCategory(category);
        }
        return book;
    }
}

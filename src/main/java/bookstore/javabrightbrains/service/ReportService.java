package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.book.BookNotPaidDto;
import bookstore.javabrightbrains.dto.book.TopBookDto;

import java.util.List;

public interface ReportService {
    List<TopBookDto> getTopPurchasedBooks();

    List<TopBookDto> getTopPCancelledBooks();

    List<BookNotPaidDto> getPendingBooksOlderThan(int days);
}

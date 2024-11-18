package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.book.BookNotPaidDto;
import bookstore.javabrightbrains.dto.book.ProfitDto;
import bookstore.javabrightbrains.dto.book.TopBookDto;
import bookstore.javabrightbrains.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<TopBookDto> getTopPurchasedBooks() {
        Pageable pagination = PageRequest.of(0, 10);
        return orderItemRepository.findTopBooksByDeliveredOrders(pagination);
    }

    @Override
    public List<TopBookDto> getTopPCancelledBooks() {
        Pageable pagination = PageRequest.of(0, 10);
        return orderItemRepository.findTopCancelledBooks(pagination);
    }

    @Override
    public List<BookNotPaidDto> getPendingBooksOlderThan(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return orderItemRepository.findPendingBooksOlderThan(cutoffDate);
    }

    @Override
    public List<ProfitDto> getProfit(LocalDateTime startDate, LocalDateTime endDate, String groupBy) {
        return orderItemRepository.findProfitGroupedBy(startDate, endDate, groupBy);
    }


}

package bookstore.javabrightbrains.service.impl;

import bookstore.javabrightbrains.dto.book.BookNotPaidDto;
import bookstore.javabrightbrains.dto.book.ProfitDto;
import bookstore.javabrightbrains.dto.book.TopBookDto;
import bookstore.javabrightbrains.exception.GroupByInvalidException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.exception.TimeInvalidException;
import bookstore.javabrightbrains.repository.OrderItemRepository;
import bookstore.javabrightbrains.service.ReportService;
import bookstore.javabrightbrains.utils.Constants;
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
        if (startDate.isAfter(LocalDateTime.now())) {
            throw new TimeInvalidException(MessagesException.START_DATE_CANNOT_BE_AFTER_NOW);
        }

        if (startDate.isAfter(endDate)) {
            throw new TimeInvalidException(MessagesException.START_DATE_CANNOT_BE_AFTER_END_DATE);
        }

        if (Constants.getGroupByProfit().stream().noneMatch(g -> g.equals(groupBy))) {
            throw new GroupByInvalidException(MessagesException.GROUP_BY_INVALID);
        }
        return orderItemRepository.findProfitGroupedBy(startDate, endDate, groupBy);
    }


}

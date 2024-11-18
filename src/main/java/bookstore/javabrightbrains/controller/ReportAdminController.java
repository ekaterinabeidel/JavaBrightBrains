package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookNotPaidDto;
import bookstore.javabrightbrains.dto.book.ProfitDto;
import bookstore.javabrightbrains.dto.book.TopBookDto;
import bookstore.javabrightbrains.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;

@RestController
@RequestMapping(ADMIN_BASE_URL + "/reports")
@Tag(name = "Reports Admin Controller", description = "APIs for reports for administrators")
public class ReportAdminController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/top-purchased-products")
    public ResponseEntity<List<TopBookDto>> getTopPurchasedBooks() {
      return ResponseEntity.ok(reportService.getTopPurchasedBooks())  ;
    }

    @GetMapping("/top-cancelled-products")
    public ResponseEntity<List<TopBookDto>> getTopCancelledBooks() {
        return ResponseEntity.ok(reportService.getTopPCancelledBooks())  ;
    }

    @GetMapping("/pending-payment-products/{days}")
    public ResponseEntity<List<BookNotPaidDto>> getPendingBooksOlderThan(@PathVariable int days) {
        return ResponseEntity.ok(reportService.getPendingBooksOlderThan(days))  ;
    }

    @GetMapping("/revenue/{startDate}/{endDate}/{groupBy}")
    public ResponseEntity<List<ProfitDto>> getProfit(
            @PathVariable LocalDateTime startDate,
            @PathVariable LocalDateTime endDate,
            @PathVariable String groupBy
    ) {
        return ResponseEntity.ok(reportService.getProfit(startDate, endDate, groupBy));
    }

}

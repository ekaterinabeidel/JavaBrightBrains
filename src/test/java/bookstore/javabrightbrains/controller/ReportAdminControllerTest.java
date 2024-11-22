package bookstore.javabrightbrains.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schemaTest.sql")
@Sql("/dataTest.sql")
@WithMockUser(value = "Admin", password = "password123", authorities = "ADMIN")
class ReportAdminControllerTest {

    @Test
    void getTopPurchasedBooksSucc() {
    }

    @Test
    void getTopCancelledBooks() {
    }

    @Test
    void getPendingBooksOlderThan() {
    }

    @Test
    void getProfit() {
    }
}
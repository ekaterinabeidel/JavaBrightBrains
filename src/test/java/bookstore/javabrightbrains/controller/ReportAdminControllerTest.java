package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.exception.GroupByInvalidException;
import bookstore.javabrightbrains.exception.MessagesException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schemaTest.sql")
@Sql("/dataTest.sql")
@WithMockUser(value = "Admin", password = "password123", authorities = "ADMIN")
class ReportAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTopPurchasedBooksSuccess() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_BASE_URL + "/reports/top-purchased-products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        List list = objectMapper.readValue(jsonResult, List.class);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void getTopCancelledBooksSuccess() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_BASE_URL + "/reports/top-cancelled-products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        List list = objectMapper.readValue(jsonResult, List.class);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void getPendingBooksOlderThanSuccess() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_BASE_URL + "/reports/pending-payment-products/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        List list = objectMapper.readValue(jsonResult, List.class);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void getProfitSuccess() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1000);
        LocalDateTime endDate = LocalDateTime.now();
        String groupBy = "day";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_BASE_URL + "/reports/revenue/{startDate}/{endDate}/{groupBy}",
                                startDate,
                                endDate,
                                groupBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        List list = objectMapper.readValue(jsonResult, List.class);
        Assertions.assertFalse(list.isEmpty());
    }
    @Test
    void getProfitGroupByInvalidException() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1000);
        LocalDateTime endDate = LocalDateTime.now();
        String groupBy = "days";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_BASE_URL + "/reports/revenue/{startDate}/{endDate}/{groupBy}",
                                startDate,
                                endDate,
                                groupBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        GroupByInvalidException res = objectMapper.readValue(jsonResult, GroupByInvalidException.class);
        Assertions.assertEquals(MessagesException.GROUP_BY_INVALID, res.getMessage());
    }

    @Test
    void getProfitTimeInvalidExceptionStartDate() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().plusDays(1000);
        LocalDateTime endDate = LocalDateTime.now();
        String groupBy = "day";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_BASE_URL + "/reports/revenue/{startDate}/{endDate}/{groupBy}",
                                startDate,
                                endDate,
                                groupBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        GroupByInvalidException res = objectMapper.readValue(jsonResult, GroupByInvalidException.class);
        Assertions.assertEquals(MessagesException.START_DATE_CANNOT_BE_AFTER_NOW, res.getMessage());
    }

    @Test
    void getProfitTimeInvalidException() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(100);
        LocalDateTime endDate = LocalDateTime.now().minusDays(1000);
        String groupBy = "day";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ADMIN_BASE_URL + "/reports/revenue/{startDate}/{endDate}/{groupBy}",
                                startDate,
                                endDate,
                                groupBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        GroupByInvalidException res = objectMapper.readValue(jsonResult, GroupByInvalidException.class);
        Assertions.assertEquals(MessagesException.START_DATE_CANNOT_BE_AFTER_END_DATE, res.getMessage());
    }
}
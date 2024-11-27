package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.order.OrderRequestDto;
import bookstore.javabrightbrains.dto.order.OrderResponseDto;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.enums.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schemaTest.sql")
@Sql("/dataTest.sql")
@WithMockUser(value = "user1@example.com", password = "password123", authorities = "USER")
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrderSuccess() throws Exception {

        OrderRequestDto requestDto = new OrderRequestDto(
                1L, "123 Main St", "+1234567890", "Standard");
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_BASE_URL + "/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        OrderResponseDto orderResponse = objectMapper.readValue(jsonResult, OrderResponseDto.class);

        Assertions.assertNotNull(orderResponse.getId());
        Assertions.assertEquals(requestDto.getDeliveryAddress(), orderResponse.getDeliveryAddress());
        Assertions.assertEquals(requestDto.getContactPhone(), orderResponse.getContactPhone());
    }

    @Test
    void createOrderInvalidPhoneNumber() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto(
                1L, "123 Main St", "invalid_phone", "Standard");
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post(USER_BASE_URL + "/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.contactPhone").value("Invalid phone number format"));
    }

    @Test
    void createOrderWithInvalidDeliveryMethod() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto(1L, "123 Main St", "+1234567890", "InvalidMethod");
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.deliveryMethod").value("Invalid delivery method. Allowed values are: Standard, Express"));
    }

    @Test
    void createOrderCartNotFoundException() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto(100L, "123 Main St", "+1234567890", "Standard");
        String json = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MessagesException.CART_NOT_FOUND));
    }

    @Test
    void getOrderByIdSuccess() throws Exception {
        Long orderId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/orders/get/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId));
    }

    @Test
    void getOrderByIdNotFoundException() throws Exception {
        Long nonExistentOrderId = 100L;

        mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/orders/get/{id}", nonExistentOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MessagesException.ORDER_NOT_FOUND));
    }

    @Test
    void getOrdersByUserIdSuccess() throws Exception {
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/orders/get-orders/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    @Test
    void getOrdersByUserIdUserNotFoundException() throws Exception {
        Long nonExistentUserId = 999L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/orders/get-orders/{userId}", nonExistentUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MessagesException.USER_NOT_FOUND));
    }

    @Test
    void cancelOrderSuccess() throws Exception {
        Long orderId = 5L;

        mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/orders/update/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(OrderStatus.CANCELED.getStatus()));
    }

    @Test
    void cancelOrderCancellationNotAllowedException() throws Exception {
        Long orderIdWithIneligibleStatus = 2L;

        mockMvc.perform(MockMvcRequestBuilders
                        .put(USER_BASE_URL + "/orders/update/{orderId}", orderIdWithIneligibleStatus)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(MessagesException.ORDER_CANNOT_BE_CANCELED_INVALID_STATUS));
    }

    @Test
    void getPurchaseHistorySuccess() throws Exception {
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/orders/history/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    @Test
    void getPurchaseHistoryUserNotFoundException() throws Exception {
        Long nonExistentUserId = 999L;

        mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/orders/history/{userId}", nonExistentUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(MessagesException.USER_NOT_FOUND));
    }

    @Test
    @WithMockUser(value = "user7@example.com", password = "password123", authorities = "USER")
    void getPurchaseHistoryOrderNotFoundException() throws Exception {
        Long userIdWithoutOrders = 7L;

        mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/orders/history/{userId}", userIdWithoutOrders)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
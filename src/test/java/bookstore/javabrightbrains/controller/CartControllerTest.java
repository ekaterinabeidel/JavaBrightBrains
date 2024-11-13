package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.cart.CartItemRequestDto;
import bookstore.javabrightbrains.dto.cart.CartItemUpdateRequestDto;
import bookstore.javabrightbrains.dto.cart.CartResponseDto;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schemaTest.sql")
@Sql("/dataTest.sql")
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCartSuccess() throws Exception{
        Long userId = 1L;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/{userId}/cart", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        CartResponseDto cartResponseDto = objectMapper.readValue(jsonResult,  CartResponseDto.class);

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertNotNull(cartResponseDto);
    }

    @Test
    void getCartNotFoundUserException() throws Exception {
        Long userId = 1000L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/{userId}/cart", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.USER_NOT_FOUND, response.getMessage());
    }

    @Test
    void addToCartSuccess() throws Exception {
        Long userId = 1L;
        CartItemRequestDto requestDto = new CartItemRequestDto(1L, 10);
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_BASE_URL + "/{userId}/cart/items", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        Assertions.assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void addToCartNotFoundUserException() throws Exception {
        Long userId = 1000L;
        CartItemRequestDto requestDto = new CartItemRequestDto(1L, 10);
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_BASE_URL + "/{userId}/cart/items", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.USER_NOT_FOUND, response.getMessage());
    }

    @Test
    void updateCartItemSuccess() throws Exception {
        Long userId = 1L;
        Long cartItemId = 1L;
        CartItemUpdateRequestDto updateRequestDto = new CartItemUpdateRequestDto(5);
        String json = objectMapper.writeValueAsString(updateRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/{userId}/cart/items/{cartItemId}", userId, cartItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        Assertions.assertEquals(204, result.getResponse().getStatus());
    }

    @Test
    void updateCartItemNotFoundException() throws Exception {
        Long userId = 1L;
        Long cartItemId = 1000L;
        CartItemUpdateRequestDto updateRequestDto = new CartItemUpdateRequestDto(5);
        String json = objectMapper.writeValueAsString(updateRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/{userId}/cart/items/{cartItemId}", userId, cartItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.CART_ITEM_NOT_FOUND, response.getMessage());
    }
    @Test
    void deleteCartItemSuccess() throws Exception {
        Long userId = 1L;
        Long cartItemId = 1L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(USER_BASE_URL + "/{userId}/cart/items/{cartItemId}", userId, cartItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(204, result.getResponse().getStatus());
    }

    @Test
    void deleteCartItemNotFoundException() throws Exception {
        Long userId = 1L;
        Long cartItemId = 1000L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(USER_BASE_URL + "/{userId}/cart/items/{cartItemId}", userId, cartItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.CART_ITEM_NOT_FOUND, response.getMessage());
    }

}
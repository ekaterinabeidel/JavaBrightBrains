package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.favorite.FavoriteRequestDto;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
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

import java.util.List;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schemaTest.sql")
@Sql("/dataTest.sql")
@WithMockUser(value = "User", password = "password123", authorities = "USER")
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addFavoriteSuccess() throws Exception {
        FavoriteRequestDto requestDto = new FavoriteRequestDto(
                1L, 4L
        );
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_BASE_URL + "/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        BookShortResponseDto book;
        book = objectMapper.readValue(jsonResult, BookShortResponseDto.class);

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(requestDto.getBookId(), book.getId());
    }

    @Test
    void addFavoriteNotFoundUserException() throws Exception {
        FavoriteRequestDto requestDto = new FavoriteRequestDto(
                10L, 4L
        );
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_BASE_URL + "/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        IdNotFoundException response;
        response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.USER_NOT_FOUND, response.getMessage());
    }

    @Test
    void addFavoriteNotFoundBookException() throws Exception {
        FavoriteRequestDto requestDto = new FavoriteRequestDto(
                1L, 40L
        );
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_BASE_URL + "/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        IdNotFoundException response;
        response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.BOOK_NOT_FOUND, response.getMessage());
    }

    @Test
    void getFavoritesSuccess() throws Exception {
        Long userId = 1L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                USER_BASE_URL + "/favorites/{userId}",
                                userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        List<BookShortResponseDto> response;
        response = objectMapper.readValue(jsonResult, List.class);

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(2, response.size());
    }

    @Test
    void getFavoritesNotFoundUserException() throws Exception {
        Long userId = 1000L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                USER_BASE_URL + "/favorites/{userId}",
                                userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        IdNotFoundException response;
        response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.USER_NOT_FOUND, response.getMessage());
    }

    @Test
    void deleteFavoritesSuccess() throws Exception {
        FavoriteRequestDto requestDto = new FavoriteRequestDto(
                1L, 1L
        );

        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(
                                USER_BASE_URL + "/favorites",
                                requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        result.getResponse().getContentAsString();

        Assertions.assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    void deleteFavoritesNotFoundBookException() throws Exception {
        FavoriteRequestDto requestDto = new FavoriteRequestDto(
                1L, 10L
        );

        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(
                                USER_BASE_URL + "/favorites",
                                requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response;
        response = objectMapper.readValue(jsonResult, IdNotFoundException.class);
        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.BOOK_NOT_FOUND, response.getMessage());
    }

    @Test
    void deleteFavoritesNotFoundUserException() throws Exception {
        FavoriteRequestDto requestDto = new FavoriteRequestDto(
                10L, 1L
        );

        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(
                                USER_BASE_URL + "/favorites",
                                requestDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response;
        response = objectMapper.readValue(jsonResult, IdNotFoundException.class);
        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.USER_NOT_FOUND, response.getMessage());
    }
}
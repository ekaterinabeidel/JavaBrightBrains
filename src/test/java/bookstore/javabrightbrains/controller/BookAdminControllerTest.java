package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookRequestDto;
import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.BookRepository;
import bookstore.javabrightbrains.repository.FavoriteRepository;
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


import java.math.BigDecimal;

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schemaTest.sql")
@Sql("/dataTest.sql")
@WithMockUser(value = "Admin", password = "password123", authorities = "ADMIN")
class BookAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void addBookSuccess() throws Exception {
        BookRequestDto requestDto = new BookRequestDto(
                "New Book", "Author Name", "Book description", new BigDecimal("19.99"), 10, 1L, 100, "http://example.com/image.jpg");
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ADMIN_BASE_URL + "/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        BookResponseDto responseDto = objectMapper.readValue(jsonResult, BookResponseDto.class);

        Assertions.assertEquals(201, result.getResponse().getStatus());
        Assertions.assertEquals(requestDto.getTitle(), responseDto.getTitle());
    }

    @Test
    void updateBookSuccess() throws Exception {
        Long bookId = 1L;
        BookRequestDto requestDto = new BookRequestDto(
                "Updated Book", "Updated Author", "Updated description", new BigDecimal("29.99"), 15, 1L, 200, "http://example.com/updated_image.jpg");
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ADMIN_BASE_URL + "/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        BookResponseDto responseDto = objectMapper.readValue(jsonResult, BookResponseDto.class);

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(requestDto.getTitle(), responseDto.getTitle());
    }

    @Test
    void updateBookNotFoundException() throws Exception {
        Long bookId = 999L;
        BookRequestDto requestDto = new BookRequestDto(
                "Updated Book", "Updated Author", "Updated description", new BigDecimal("29.99"), 15, 1L, 200, "http://example.com/updated_image.jpg");
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ADMIN_BASE_URL + "/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.BOOK_NOT_FOUND, response.getMessage());
    }

    @Test
    void deleteBookSuccess() throws Exception {
        Long bookId = 1L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(ADMIN_BASE_URL + "/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteBookNotFoundException() throws Exception {
        Long bookId = 999L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(ADMIN_BASE_URL + "/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.BOOK_NOT_FOUND, response.getMessage());
    }

    @Test
    void deleteBookCascadeSuccess() throws Exception {
        Long bookId = 1L;

        Assertions.assertTrue(bookRepository.existsById(bookId));
        Assertions.assertTrue(favoriteRepository.findAll().stream()
                .anyMatch(favorite -> favorite.getBook().getId().equals(bookId)));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(ADMIN_BASE_URL + "/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertFalse(bookRepository.existsById(bookId));
        Assertions.assertTrue(favoriteRepository.findAll().stream()
                .noneMatch(favorite -> favorite.getBook().getId().equals(bookId)));
    }

    @Test
    void deleteBookCascadeNotFound() throws Exception {
        Long bookId = 999L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(ADMIN_BASE_URL + "/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertEquals(404, result.getResponse().getStatus());
    }

}

package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookRequestDto;
import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.BookRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schemaTest.sql")
@Sql("/dataTest.sql")
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void getAllBooks() {
    }

    @Test
    void getBookSuccessDetail() throws Exception {
        Long bookId = 1L;


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        BookResponseDto responseDto = objectMapper.readValue(jsonResult, BookResponseDto.class);

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertNotNull(responseDto.getDescription());
    }

    @Test
    void getBookNotFoundDetail() throws Exception {
        Long bookId = 10000L;


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.BOOK_NOT_FOUND, response.getMessage());
    }
}
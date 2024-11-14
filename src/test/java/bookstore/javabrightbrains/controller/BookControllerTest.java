package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.book.BookResponseDto;
import bookstore.javabrightbrains.dto.book.BookShortResponseDto;
import bookstore.javabrightbrains.dto.book.PageResponseDto;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.repository.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

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
    void getAllPaginatingBooks() throws Exception {
        int pageNum = 1;
        int pageSize = 2;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/books/pageNumber/{pageNum}/pageSize/{pageSize}",
                                pageNum,
                                pageSize)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();


        String jsonResult = result.getResponse().getContentAsString();
        PageResponseDto<BookShortResponseDto> dto = objectMapper.readValue(jsonResult, PageResponseDto.class);


        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(pageSize, dto.getContent().size());
        Assertions.assertEquals(pageNum, dto.getPageNumber());
    }

    @Test
    void getAllCategoryFilteringBooks() throws Exception {
        int pageNum = 1;
        int pageSize = 10;
        Long categoryId = 1L;


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/books/pageNumber/{pageNum}/pageSize/{pageSize}?categoryId={categoryId}", pageNum, pageSize, categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();


        String jsonResult = result.getResponse().getContentAsString();
        PageResponseDto<BookShortResponseDto> dto = objectMapper.readValue(jsonResult, new TypeReference<PageResponseDto<BookShortResponseDto>>() {
        });
        List<BookShortResponseDto> content = dto.getContent();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        for (int i = 0; i < dto.getContent().size() - 1; i++) {
            Assertions.assertEquals(categoryId, content.get(i).getCategoryId());
        }
    }

    @Test
    void getAllCategoryExceptionFilteringBooks() throws Exception {
        int pageNum = 1;
        int pageSize = 10;
        Long categoryId = 100L;


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/books/pageNumber/{pageNum}/pageSize/{pageSize}?categoryId={categoryId}", pageNum, pageSize, categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();


        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException exception = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());

        Assertions.assertEquals(MessagesException.CATEGORY_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getAllPriceFilteringBooks() throws Exception {
        int pageNumber = 1;
        int pageSize = 10;

        int minPrice = 0;
        int maxPrice = 10;


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/books/pageNumber/{pageNumber}/pageSize/{pageSize}?minPrice={minPrice}&maxPrice={maxPrice}",
                                pageNumber, pageSize, minPrice, maxPrice)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();


        String jsonResult = result.getResponse().getContentAsString();
        PageResponseDto<BookShortResponseDto> dto = objectMapper.readValue(jsonResult, new TypeReference<PageResponseDto<BookShortResponseDto>>() {
        });
        List<BookShortResponseDto> content = dto.getContent();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        for (int i = 0; i < dto.getContent().size() - 1; i++) {
            Assertions.assertTrue(content.get(i).getPriceDiscount().compareTo(BigDecimal.valueOf(minPrice)) >= 0);
            Assertions.assertTrue(content.get(i).getPriceDiscount().compareTo(BigDecimal.valueOf(maxPrice)) <= 0);
        }
    }

    @Test
    void getAllMaxPriceFilteringBooks() throws Exception {
        int pageNumber = 1;
        int pageSize = 10;


        int maxPrice = 10;


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/books/pageNumber/{pageNumber}/pageSize/{pageSize}?maxPrice={maxPrice}",
                                pageNumber, pageSize, maxPrice)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();


        String jsonResult = result.getResponse().getContentAsString();
        PageResponseDto<BookShortResponseDto> dto = objectMapper.readValue(jsonResult, new TypeReference<PageResponseDto<BookShortResponseDto>>() {
        });
        List<BookShortResponseDto> content = dto.getContent();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        for (int i = 0; i < dto.getContent().size() - 1; i++) {
            Assertions.assertTrue(content.get(i).getPriceDiscount().compareTo(BigDecimal.valueOf(maxPrice)) <= 0);
        }
    }

    @Test
    void getAllDiscountFilteringBooks() throws Exception {
        int pageNumber = 1;
        int pageSize = 10;


        boolean isDiscount = true;


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/books/pageNumber/{pageNumber}/pageSize/{pageSize}?isDiscount={maxPrice}",
                                pageNumber, pageSize, isDiscount)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();


        String jsonResult = result.getResponse().getContentAsString();
        PageResponseDto<BookShortResponseDto> dto = objectMapper.readValue(jsonResult, new TypeReference<PageResponseDto<BookShortResponseDto>>() {
        });
        List<BookShortResponseDto> content = dto.getContent();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        for (int i = 0; i < dto.getContent().size() - 1; i++) {
            Assertions.assertTrue(content.get(i).getDiscount() > 0);
        }
    }

    @Test
    void getAllMinPriceFilteringBooks() throws Exception {
        int pageNumber = 1;
        int pageSize = 10;

        int minPrice = 10;


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/books/pageNumber/{pageNumber}/pageSize/{pageSize}?minPrice={minPrice}",
                                pageNumber, pageSize, minPrice)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();


        String jsonResult = result.getResponse().getContentAsString();
        PageResponseDto<BookShortResponseDto> dto = objectMapper.readValue(jsonResult, new TypeReference<PageResponseDto<BookShortResponseDto>>() {
        });
        List<BookShortResponseDto> content = dto.getContent();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        for (int i = 0; i < dto.getContent().size() - 1; i++) {
            Assertions.assertTrue(content.get(i).getPriceDiscount().compareTo(BigDecimal.valueOf(minPrice)) >= 0);
        }
    }

    @Test
    void getAllSortingFilteringBooks() throws Exception {
        int pageNumber = 1;
        int pageSize = 10;

        String sortDirect = "desc";
        String sortBy = "price";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/api/books/pageNumber/{pageNumber}/pageSize/{pageSize}?sortBy={sortBy}&sortDirect={sortDirect}",
                                pageNumber, pageSize, sortBy, sortDirect)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn();


        String jsonResult = result.getResponse().getContentAsString();
        PageResponseDto<BookShortResponseDto> dto = objectMapper.readValue(jsonResult, new TypeReference<PageResponseDto<BookShortResponseDto>>() {
        });
        List<BookShortResponseDto> content = dto.getContent();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        BookShortResponseDto min =  content.stream().min(Comparator.comparing(BookShortResponseDto::getPrice)).get();
        BookShortResponseDto max =  content.stream().max(Comparator.comparing(BookShortResponseDto::getPrice)).get();

        Assertions.assertEquals(min.getId(), content.getLast().getId());
        Assertions.assertEquals(max.getId(), content.getFirst().getId());
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
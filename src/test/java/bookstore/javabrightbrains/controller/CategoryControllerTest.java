package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.category.CategoryDto;
import bookstore.javabrightbrains.dto.category.CategoryRequestDto;
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

import static bookstore.javabrightbrains.utils.Constants.ADMIN_BASE_URL;
import static bookstore.javabrightbrains.utils.Constants.PUBLIC_BASE_URL;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schemaTest.sql")
@Sql("/dataTest.sql")
@WithMockUser(value = "Admin", password = "password123", authorities = "ADMIN")
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCategoriesSuccess() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PUBLIC_BASE_URL + "/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        List<CategoryDto> categories = objectMapper.readValue(jsonResult, List.class);

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertFalse(categories.isEmpty());
    }

    @Test
    void addCategorySuccess() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto("New Category");
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ADMIN_BASE_URL + "/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        Assertions.assertEquals(201, result.getResponse().getStatus());

        String jsonResult = result.getResponse().getContentAsString();
        CategoryDto responseDto = objectMapper.readValue(jsonResult, CategoryDto.class);

        Assertions.assertEquals(requestDto.getName(), responseDto.getName());
    }

    @Test
    void updateCategorySuccess() throws Exception {
        Long categoryId = 1L;
        CategoryRequestDto requestDto = new CategoryRequestDto("Updated Category");
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ADMIN_BASE_URL + "/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        CategoryDto responseDto = objectMapper.readValue(jsonResult, CategoryDto.class);

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(requestDto.getName(), responseDto.getName());
    }

    @Test
    void updateCategoryNotFoundException() throws Exception {
        Long categoryId = 999L;
        CategoryRequestDto requestDto = new CategoryRequestDto("Updated Category");
        String json = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ADMIN_BASE_URL + "/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.CATEGORY_NOT_FOUND, response.getMessage());
    }

    @Test
    void deleteCategorySuccess() throws Exception {
        Long categoryId = 1L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(ADMIN_BASE_URL + "/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteCategoryNotFoundException() throws Exception {
        Long categoryId = 999L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(ADMIN_BASE_URL + "/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException response = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.CATEGORY_NOT_FOUND, response.getMessage());
    }
}

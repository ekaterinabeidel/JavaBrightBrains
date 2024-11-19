package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.service.JwtSecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static bookstore.javabrightbrains.utils.Constants.USER_BASE_URL;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/schemaTest.sql")
@Sql("/dataTest.sql")
@WithMockUser(value = "User", password = "password123", authorities = "USER")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtSecurityService jwtSecurityService;

    @Test
    void getUserSuccess() throws Exception {
        Long userId = 1L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(jsonResult, UserDto.class);

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals("Yuri", userDto.getName());
        Assertions.assertEquals("Gagarin", userDto.getSurname());
    }

    @Test
    void getUserNotFoundException() throws Exception {
        Long userId = 999L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_BASE_URL + "/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException exception = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void updateUserSuccess() throws Exception {
        Long userId = 1L;

        UserDto userDto = new UserDto();
        userDto.setName("Updated Name");
        userDto.setSurname("Updated Surname");
        userDto.setEmail("updatedemail@example.com");
        userDto.setPhone("+123 1234567890");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        UserDto updatedUser = objectMapper.readValue(jsonResult, UserDto.class);

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals(userDto.getName(), updatedUser.getName());
        Assertions.assertEquals(userDto.getSurname(), updatedUser.getSurname());
        Assertions.assertEquals(userDto.getEmail(), updatedUser.getEmail());
    }

    @Test
    void updateUserNotFoundException() throws Exception {
        Long userId = 999L;
        UserDto userDto = new UserDto();
        userDto.setName("Updated Name");
        userDto.setSurname("Updated Surname");
        userDto.setEmail("updatedemail@example.com");
        userDto.setPhone("+123 1234567890");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException exception = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void deleteUserSuccess() throws Exception {
        Long userId = 1L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(USER_BASE_URL + "/delete/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(204, result.getResponse().getStatus());
    }

    @Test
    void deleteUserNotFoundException() throws Exception {
        Long userId = 999L;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(USER_BASE_URL + "/delete/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        IdNotFoundException exception = objectMapper.readValue(jsonResult, IdNotFoundException.class);

        Assertions.assertEquals(404, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.USER_NOT_FOUND, exception.getMessage());
    }
}

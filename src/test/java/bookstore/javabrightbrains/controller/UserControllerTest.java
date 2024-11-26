package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.user.UserDto;
import bookstore.javabrightbrains.exception.EmailDuplicateException;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.service.JwtSecurityService;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.Map;

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
        userDto.setEmail("user1@example.com");
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
        userDto.setEmail("user2@example.com");
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
    void updateEmailDuplicateException() throws Exception {
        Long userId = 1L;

        UserDto userDto = new UserDto();
        userDto.setName("Updated Name");
        userDto.setSurname("Updated Surname");
        userDto.setEmail("user2@example.com");
        userDto.setPhone("+123 1234567890");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        EmailDuplicateException exception = objectMapper.readValue(jsonResult, EmailDuplicateException.class);

        Assertions.assertEquals(409, result.getResponse().getStatus());
        Assertions.assertEquals(MessagesException.DUPLICATED_EMAIL, exception.getMessage());
    }


    @Test
    void updateUserValidationFailedNameSize() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("");
        userDto.setSurname("Updated Surname");
        userDto.setEmail("user2@example.com");
        userDto.setPhone("+1234567890");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Name must be between 1 and 50 characters", errors.get("name"));
    }

    @Test
    void updateUserValidationFailedNameRequired() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName(null);
        userDto.setSurname("Gagarin");
        userDto.setEmail("user2@example.com");
        userDto.setPhone("+1234567890");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Name is required", errors.get("name"));
    }

    @Test
    void updateUserValidationFailedSurnameSize() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("Updated Name");
        userDto.setSurname("");
        userDto.setEmail("user2@example.com");
        userDto.setPhone("+1234567890");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Surname must be between 1 and 50 characters", errors.get("surname"));
    }

    @Test
    void updateUserValidationFailedSurnameRequired() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("Yuri");
        userDto.setSurname(null);
        userDto.setEmail("user2@example.com");
        userDto.setPhone("+1234567890");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Surname is required", errors.get("surname"));
    }

    @Test
    void updateUserValidationFailedEmail() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("Updated Name");
        userDto.setSurname("Updated Surname");
        userDto.setEmail("user2example.com");
        userDto.setPhone("+1234567890");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Email should be valid", errors.get("email"));
    }

    @Test
    void updateUserValidationFailedEmailRequired() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("Yuri");
        userDto.setSurname("Gagarin");
        userDto.setEmail(null);
        userDto.setPhone("+1234567890");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Email is required", errors.get("email"));
    }

    @Test
    void updateUserValidationFailedPhone() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("Updated Name");
        userDto.setSurname("Updated Surname");
        userDto.setEmail("user2example.com");
        userDto.setPhone("+123");
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Phone number is invalid", errors.get("phone"));
    }

    @Test
    void updateUserValidationFailedPhoneRequired() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("Yuri");
        userDto.setSurname("Gagarin");
        userDto.setEmail("user2@example.com");
        userDto.setPhone(null);
        String json = objectMapper.writeValueAsString(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(USER_BASE_URL + "/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Phone number is required", errors.get("phone"));
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

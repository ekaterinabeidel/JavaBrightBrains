package bookstore.javabrightbrains.controller;

import bookstore.javabrightbrains.dto.auth.*;
import bookstore.javabrightbrains.exception.MessagesException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerNewUserSuccess() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setName("Ivan");
        registerRequest.setSurname("Ivanov");
        registerRequest.setEmail("ivan@gmail.com");
        registerRequest.setPassword("password123");

        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        RegisterResponseDto responseDto = objectMapper.readValue(jsonResponse, RegisterResponseDto.class);
        Assertions.assertEquals("Ivan", responseDto.getName());
        Assertions.assertEquals("Ivanov", responseDto.getSurname());
        Assertions.assertEquals("ivan@gmail.com", responseDto.getEmail());
        Assertions.assertNotNull(responseDto.getId());
    }

    @Test
    void registerEmailDuplicateException() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setName("Yuri");
        registerRequest.setSurname("Gagarin");
        registerRequest.setEmail("user1@example.com");
        registerRequest.setPassword("password123");

        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(409, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.contains(MessagesException.DUPLICATED_EMAIL));
    }

    @Test
    void registerValidationFailedNameSize() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setName("I".repeat(51));
        registerRequest.setSurname("Ivanov");
        registerRequest.setEmail("ivaniv@example.com");
        registerRequest.setPassword("password123");

        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Name must be between 1 and 50 characters", errors.get("name"));
    }

    @Test
    void registerValidationFailedNameRequired() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setName(null);
        registerRequest.setSurname("Ivanov");
        registerRequest.setEmail("ivaniv@example.com");
        registerRequest.setPassword("password123");

        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Name is required", errors.get("name"));
    }

    @Test
    void registerValidationFailedSurnameSize() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setName("Ivan");
        registerRequest.setSurname("I".repeat(51));
        registerRequest.setEmail("ivan@example.com");
        registerRequest.setPassword("password123");

        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Surname must be between 1 and 50 characters", errors.get("surname"));
    }

    @Test
    void registerValidationFailedEmail() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setName("Ivan");
        registerRequest.setSurname("Ivanov");
        registerRequest.setEmail("ivan2example.com");
        registerRequest.setPassword("password123");

        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Email should be valid", errors.get("email"));
    }

    @Test
    void registerValidationFailedEmailRequired() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setName("Ivan");
        registerRequest.setSurname("Ivanov");
        registerRequest.setEmail(null);
        registerRequest.setPassword("password123");

        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Email is required", errors.get("email"));
    }

    @Test
    void registerValidationFailedPassword() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setName("Ivan");
        registerRequest.setSurname("Ivanov");
        registerRequest.setEmail("ivan@example.com");
        registerRequest.setPassword("123");

        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Password must be at least 8 characters long", errors.get("password"));
    }

    @Test
    void registerValidationFailedPasswordRequired() throws Exception {
        RegisterRequestDto registerRequest = new RegisterRequestDto();
        registerRequest.setName("Ivan");
        registerRequest.setSurname("Ivanov");
        registerRequest.setEmail("ivan@example.com");
        registerRequest.setPassword(null);

        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResult, new TypeReference<>() {
        });

        Assertions.assertEquals(400, result.getResponse().getStatus());
        Assertions.assertEquals("Password is required", errors.get("password"));
    }

    @Test
    void loginSuccess() throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("user1@example.com");
        loginRequest.setPassword("password123");

        String jsonRequest = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        LoginResponseDto responseDto = objectMapper.readValue(jsonResponse, LoginResponseDto.class);

        Assertions.assertEquals("user1@example.com", responseDto.getEmail());
        Assertions.assertNotNull(responseDto.getJwtToken());
        Assertions.assertNotNull(responseDto.getRefreshToken());
    }

    @Test
    void loginInvalidPassword() throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("user1@example.com");
        loginRequest.setPassword("123456789");

        String jsonRequest = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(401, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.contains("Invalid username or password"));
    }

    @Test
    void loginUserNotFound() throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("nonexistent@example.com");
        loginRequest.setPassword("password123");

        String jsonRequest = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(401, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.contains("Invalid username or password"));
    }

    @Test
    void loginValidationFailedEmailRequired() throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail(""); // Пустой email
        loginRequest.setPassword("123123123");

        String jsonRequest = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(400, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        Assertions.assertEquals("Email is required", errors.get("email"));
    }

    @Test
    void loginValidationFailedPasswordRequired() throws Exception {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("user1@example.com");
        loginRequest.setPassword("");

        String jsonRequest = objectMapper.writeValueAsString(loginRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(400, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        Assertions.assertEquals("Password is required", errors.get("password"));
    }

    @Test
    void refreshSuccess() throws Exception {
        String validToken = Jwts.builder()
                .subject("user1@example.com")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("6yU3AaLTrj/YSKQtYF6yU3/YSKAaLTIv9aRtGxO" +
                        "cU39h7T/aRtGxO+syA=")))
                .compact();

        RefreshTokenRequestDto refreshTokenRequest = new RefreshTokenRequestDto();
        refreshTokenRequest.setRefreshToken(validToken);

        String jsonRequest = objectMapper.writeValueAsString(refreshTokenRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        RefreshTokenResponseDto responseDto = objectMapper.readValue(jsonResponse, RefreshTokenResponseDto.class);

        Assertions.assertNotNull(responseDto.getJwtToken());
        Assertions.assertNotNull(responseDto.getRefreshToken());
    }

    @Test
    void refreshUserNotFoundException() throws Exception {
        String validToken = Jwts.builder()
                .subject("unknown_user@example.com")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("6yU3AaLTrj/YSKQtYF6yU3/YSKAaLTIv9aRtGxO" +
                        "cU39h7T/aRtGxO+syA=")))
                .compact();

        RefreshTokenRequestDto refreshTokenRequest = new RefreshTokenRequestDto();
        refreshTokenRequest.setRefreshToken(validToken);

        String jsonRequest = objectMapper.writeValueAsString(refreshTokenRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(401, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.contains(MessagesException.USER_NOT_FOUND));
    }

    @Test
    void refreshInvalidToken() throws Exception {
        RefreshTokenRequestDto refreshTokenRequest = new RefreshTokenRequestDto();
        refreshTokenRequest.setRefreshToken("eyJA1fQ.3fIJim_1ZeVReLbHsgH4t-7cJEfiS3KzOhcDbKtCL0A");

        String jsonRequest = objectMapper.writeValueAsString(refreshTokenRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(401, result.getResponse().getStatus());
    }

    @Test
    void refreshUserNotFound() throws Exception {
        RefreshTokenRequestDto refreshTokenRequest = new RefreshTokenRequestDto();
        refreshTokenRequest.setRefreshToken("token-for-nonexistent-user");

        String jsonRequest = objectMapper.writeValueAsString(refreshTokenRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(401, result.getResponse().getStatus());
    }

    @Test
    void refreshValidationFailedExpiredToken() throws Exception {
        RefreshTokenRequestDto refreshTokenRequest = new RefreshTokenRequestDto();
        refreshTokenRequest.setRefreshToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUBleGFtcGxlLmNvbSIsImlhdCI6MTczM" +
                "jEzNjM2NSwiZXhwIjoxNzMyMTM3ODA1fQ.3fIJim_1ZeVReLbHsgH4t-7cJEfiS3KzOhcDbKtCL0A");

        String jsonRequest = objectMapper.writeValueAsString(refreshTokenRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(401, result.getResponse().getStatus());
        String jsonResponse = result.getResponse().getContentAsString();
        Assertions.assertTrue(jsonResponse.contains(MessagesException.JWT_TOKEN_EXPIRED));
    }

    @Test
    void refreshValidationFailedEmptyToken() throws Exception {
        RefreshTokenRequestDto refreshTokenRequest = new RefreshTokenRequestDto();
        refreshTokenRequest.setRefreshToken("");

        String jsonRequest = objectMapper.writeValueAsString(refreshTokenRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andReturn();

        Assertions.assertEquals(400, result.getResponse().getStatus());

        String jsonResponse = result.getResponse().getContentAsString();
        Map<String, String> errors = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        Assertions.assertEquals("Refresh token is required", errors.get("refreshToken"));
    }
}
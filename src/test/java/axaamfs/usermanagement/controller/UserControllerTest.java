package axaamfs.usermanagement.controller;

import axaamfs.usermanagement.dto.RegisterUserRequest;
import axaamfs.usermanagement.dto.UpdateUserRequest;
import axaamfs.usermanagement.dto.UserResponse;
import axaamfs.usermanagement.dto.WebResponse;
import axaamfs.usermanagement.entity.User;
import axaamfs.usermanagement.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("test");
        request.setPassword("rahasia");
        request.setName("Test");

        mockMvc.perform(post("/api/user/register").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpectAll(status().isOk()).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertEquals("OK", response.getData());
        });
    }

    @Test
    void testRegisterFailed() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("");
        request.setPassword("");
        request.setName("");

        mockMvc.perform(post("/api/user/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(status().isBadRequest())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getError());
                });
    }

    @Test
    void testRegisterDuplicate() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setName("Test");
        userRepository.save(user);

        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("test");
        request.setPassword("rahasia");
        request.setName("Test");

        mockMvc.perform(post("/api/user/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpectAll(status().isBadRequest())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });
                    assertNotNull(response.getError());
                });
    }

    @Test
    void getUserUnauthorized() throws Exception {
        mockMvc.perform(get("/api/user/current").accept(MediaType.APPLICATION_JSON).header("X-API-TOKEN", "not found")).andExpectAll(status().isUnauthorized()).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getError());
        });
    }

    @Test
    void getUserUnauthorizedTokenNotSend() throws Exception {
        mockMvc.perform(get("/api/user/current").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isUnauthorized()).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
            assertNotNull(response.getError());
        });
    }

    @Test
    void getUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setName("test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000000L);
        userRepository.save(user);
        mockMvc.perform(get("/api/user/current").accept(MediaType.APPLICATION_JSON).header("X-API-TOKEN", "test")).andExpectAll(status().isOk()).andDo(result -> {
            WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getError());
            assertEquals("test", response.getData().getUsername());
            assertEquals("test", response.getData().getName());
        });
    }

    @Test
    void getUserUnauthorizedExpiredToken() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setName("test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() - 1000000000000L);
        userRepository.save(user);
        mockMvc.perform(get("/api/user/current").accept(MediaType.APPLICATION_JSON).header("X-API-TOKEN", "test")).andExpectAll(status().isUnauthorized()).andDo(result -> {
            WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getError());
        });
    }

    @Test
    void updateUserUnauthorized() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();

        mockMvc.perform(
                        patch("/api/user/update")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpectAll(
                        status().isUnauthorized())
                .andDo(result -> {
                    WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getError());
                });
    }

    @Test
    void updateUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setName("Test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000000L);
        userRepository.save(user);

        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("Sianu");
        request.setPassword("Sianu1234");

        mockMvc.perform(
                        patch("/api/user/update")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .header("X-API-TOKEN", "test")
                )
                .andExpectAll(
                        status().isOk())
                .andDo(result -> {
                    WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getError());
                    assertEquals("test", response.getData().getUsername());
                    assertEquals("Sianu", response.getData().getName());

                    User userDb = userRepository.findByUsername(("test")).orElse(null);
                    assertTrue(BCrypt.checkpw("Sianu1234", userDb.getPassword()));
                });
    }

    @Test
    void listUserNotFound() throws Exception {

        mockMvc.perform(
                get("/api/user/list-user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andExpectAll(result -> {
            WebResponse<List<UserResponse>> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(), new TypeReference<>() {
                    }
            );
            assertNull(response.getError());
            assertEquals(0, response.getData().size());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(0, response.getPaging().getTotalPage());
            assertEquals(10, response.getPaging().getSize());
        });
    }

    @Test
    void listUserSuccess() throws Exception {
        for (int i = 0; i < 100; i++) {
            User user = new User("test" + 1, "rahasia", "Test", "test", 1000000000L);
            userRepository.save(user);
        }
        mockMvc.perform(
                get("/api/user/list-user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andExpectAll(result -> {
            WebResponse<List<UserResponse>> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(), new TypeReference<>() {
                    }
            );
            assertNull(response.getError());
            assertEquals(10, response.getData().size());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(10, response.getPaging().getSize());
        });
    }

    @Test
    void deleteFailedUsernameNotFound() throws Exception {
        User user = new User("test", "rahasia", "Test", "test", 1000000000L);
        userRepository.save(user);

        mockMvc.perform(
                delete("/api/user/delete/testsalah")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getError());
        });
    }

    @Test
    void deleteUserSuccess() throws Exception {
        User user = new User("test", "rahasia", "Test", "test", 1000000000L);
        userRepository.save(user);

        mockMvc.perform(
                delete("/api/user/delete/test")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getError());
            assertEquals("OK", response.getData());
        });
    }
}
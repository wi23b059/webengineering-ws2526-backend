package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.UserRequestDto;
import at.technikum.springrestbackend.dto.UserResponseDto;
import at.technikum.springrestbackend.dto.UserUpdateRequestDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests for {@link UserController}.
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    // -------------------------------------------------------------------------
    // GET all users
    // -------------------------------------------------------------------------

    @Test
    void getAllUsersReturnsList() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(List.of(new UserResponseDto()));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // -------------------------------------------------------------------------
    // GET user by id
    // -------------------------------------------------------------------------

    @Test
    void getUserByIdReturnsUser() throws Exception {
        UUID userId = UUID.randomUUID();

        when(userService.getUser(userId))
                .thenReturn(new UserResponseDto());

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // GET /me (authenticated)
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser(username = "testuser")
    void getMeReturnsAuthenticatedUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        when(userService.findByUsernameOptional("testuser"))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    void getMeReturnsUnauthorizedWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    // -------------------------------------------------------------------------
    // POST create user
    // -------------------------------------------------------------------------

    @Test
    void createUserReturnsCreated() throws Exception {
        UserRequestDto requestDto = new UserRequestDto();
        // Pflichtfelder optional setzen

        when(userService.createUser(any(UserRequestDto.class)))
                .thenReturn(new UserResponseDto());

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void createUserReturnsBadRequestWhenInvalid() throws Exception {
        UserRequestDto invalidDto = new UserRequestDto();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // -------------------------------------------------------------------------
    // PUT update user
    // -------------------------------------------------------------------------

    @Test
    void updateUserReturnsUpdatedUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserUpdateRequestDto updateDto = new UserUpdateRequestDto();

        when(userService.updateUser(eq(userId), any(UserUpdateRequestDto.class)))
                .thenReturn(new UserResponseDto());

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // DELETE user
    // -------------------------------------------------------------------------

    @Test
    void deleteUserReturnsNoContent() throws Exception {
        UUID userId = UUID.randomUUID();

        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }
}

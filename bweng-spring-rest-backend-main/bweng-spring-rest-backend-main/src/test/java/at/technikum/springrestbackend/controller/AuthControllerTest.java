package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.TokenRequestDto;
import at.technikum.springrestbackend.dto.TokenResponseDto;
import at.technikum.springrestbackend.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests for {@link AuthController}.
 * Tests only the web layer, AuthService is mocked.
 */
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Mocked to isolate controller behaviour.
     */
    @MockitoBean
    private AuthService authService;

    // -------------------------------------------------------------------------
    // Happy path
    // -------------------------------------------------------------------------

    @Test
    void tokenEndpointReturnsJwtWhenCredentialsAreValid() throws Exception {
        // given
        TokenResponseDto responseDto = new TokenResponseDto("dummy-jwt-token");

        when(authService.authenticate(any(TokenRequestDto.class)))
                .thenReturn(responseDto);

        TokenRequestDto requestDto = new TokenRequestDto();
        requestDto.setUsername("testuser");
        requestDto.setPassword("password123");

        // when & then
        mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("dummy-jwt-token"));
    }

    // -------------------------------------------------------------------------
    // Validation
    // -------------------------------------------------------------------------

    @Test
    void tokenEndpointReturnsBadRequestWhenRequestIsInvalid() throws Exception {
        // given: violates @NotBlank
        TokenRequestDto invalidRequest = new TokenRequestDto();
        invalidRequest.setUsername("");
        invalidRequest.setPassword("");

        // when & then
        mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}

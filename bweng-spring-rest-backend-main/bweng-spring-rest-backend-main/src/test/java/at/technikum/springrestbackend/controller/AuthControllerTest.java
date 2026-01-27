package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.dto.TokenRequestDto;
import at.technikum.springrestbackend.dto.TokenResponseDto;
import at.technikum.springrestbackend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AuthController}.
 */

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void token_ShouldReturnTokenResponseDto_WhenAuthServiceSucceeds() {
        // Arrange
        TokenRequestDto requestDto = new TokenRequestDto();
        requestDto.setUsername("testuser");
        requestDto.setPassword("password123");

        TokenResponseDto expectedResponse = new TokenResponseDto();
        expectedResponse.setToken("jwt-token");

        when(authService.authenticate(requestDto)).thenReturn(expectedResponse);

        // Act
        TokenResponseDto actualResponse = authController.token(requestDto);

        // Assert
        assertEquals(expectedResponse.getToken(), actualResponse.getToken());
        verify(authService, times(1)).authenticate(requestDto);
    }

    @Test
    void token_ShouldCallAuthServiceWithCorrectDto() {
        // Arrange
        TokenRequestDto requestDto = new TokenRequestDto();
        requestDto.setUsername("user@example.com");
        requestDto.setPassword("pass");

        when(authService.authenticate(requestDto))
                .thenReturn(new TokenResponseDto("token123"));

        // Act
        authController.token(requestDto);

        // Assert
        verify(authService, times(1)).authenticate(requestDto);
    }
}

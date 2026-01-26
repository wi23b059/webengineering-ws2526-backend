package at.technikum.springrestbackend.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleNotFound_ReturnsNotFoundResponse() {
        RuntimeException ex = new CategoryNotFoundException(404);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleNotFound(ex);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("Category not found", response.getBody().get("error"));
    }

    @Test
    void handleConflict_ReturnsConflictResponse() {
        RuntimeException ex = new EmailAlreadyExistsException("Email already exists");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleConflict(ex);

        assertEquals(409, response.getStatusCode().value());
        assertEquals("Email already exists", response.getBody().get("error"));
    }

    @Test
    void handleBadCredentials_ReturnsUnauthorizedResponse() {
        BadCredentialsException ex = new BadCredentialsException("Bad credentials");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleBadCredentials(ex);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Bad credentials", response.getBody().get("error"));
    }

    @Test
    void handleUsernameNotFound_ReturnsNotFoundResponse() {
        UsernameNotFoundException ex = new UsernameNotFoundException("User not found");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleUsernameNotFound(ex);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("User not found", response.getBody().get("error"));
    }

    @Test
    void handleValidation_ReturnsBadRequestResponse() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError = new FieldError("objectName", "field1", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidation(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("must not be null", response.getBody().get("field1"));
    }
}

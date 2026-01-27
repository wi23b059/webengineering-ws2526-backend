package at.technikum.springrestbackend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailAlreadyExistsException}.
 */

class EmailAlreadyExistsExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        // given
        String email = "test@example.com";

        // when
        EmailAlreadyExistsException exception =
                new EmailAlreadyExistsException(email);

        // then
        assertNotNull(exception);
        assertEquals(
                "Email already in use: test@example.com",
                exception.getMessage()
        );
    }

    @Test
    void shouldBeRuntimeException() {
        // given
        EmailAlreadyExistsException exception =
                new EmailAlreadyExistsException("any@example.com");

        // then
        assertTrue(exception instanceof RuntimeException);
    }
}

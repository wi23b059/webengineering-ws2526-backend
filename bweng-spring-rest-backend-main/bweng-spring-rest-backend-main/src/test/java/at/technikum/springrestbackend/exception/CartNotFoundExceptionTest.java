package at.technikum.springrestbackend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CartNotFoundException}.
 */

class CartNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        // given
        String userId = "user-123";

        // when
        CartNotFoundException exception = new CartNotFoundException(userId);

        // then
        assertNotNull(exception);
        assertEquals(
                "Cart with id user-123 not found.",
                exception.getMessage()
        );
    }

    @Test
    void shouldBeRuntimeException() {
        // given
        CartNotFoundException exception = new CartNotFoundException("any");

        // then
        assertTrue(exception instanceof RuntimeException);
    }
}

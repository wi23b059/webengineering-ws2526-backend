package at.technikum.springrestbackend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UsernameAlreadyExistsException}.
 */

class UsernameAlreadyExistsExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        String username = "alice123";

        UsernameAlreadyExistsException ex = new UsernameAlreadyExistsException(username);

        assertEquals("Username already in use: alice123", ex.getMessage());
    }
}

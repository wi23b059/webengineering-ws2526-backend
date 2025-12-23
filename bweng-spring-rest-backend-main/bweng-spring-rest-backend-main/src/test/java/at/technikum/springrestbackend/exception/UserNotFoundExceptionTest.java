package at.technikum.springrestbackend.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithIdMessage() {
        UUID id = UUID.randomUUID();

        UserNotFoundException ex = new UserNotFoundException(id);

        assertEquals("User with id " + id + " not found.", ex.getMessage());
    }

    @Test
    void shouldCreateExceptionWithEmailMessage() {
        String email = "test@example.com";

        UserNotFoundException ex = new UserNotFoundException(email);

        assertEquals("User with email " + email + " not found.", ex.getMessage());
    }
}

package at.technikum.springrestbackend.exception;

import java.util.UUID;

/**
 * Exception thrown when a user with a given id or email cannot be found.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID id) {
        super("User with id " + id + " not found.");
    }

    public UserNotFoundException(String email) {
        super("User with email " + email + " not found.");
    }
}

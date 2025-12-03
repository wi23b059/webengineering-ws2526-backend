package at.technikum.springrestbackend.exception;

/**
 * Exception thrown when a registration request attempts to use a username
 * that is already assigned to another user.
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username already in use: " + username);
    }
}

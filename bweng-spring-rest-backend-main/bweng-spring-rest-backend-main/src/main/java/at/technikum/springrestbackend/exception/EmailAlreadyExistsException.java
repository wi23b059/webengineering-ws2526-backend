package at.technikum.springrestbackend.exception;

/**
 * Exception thrown when an attempt is made to register a user with an email
 * address that already exists in the system.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Email already in use: " + email);
    }
}

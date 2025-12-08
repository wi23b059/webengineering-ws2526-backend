package at.technikum.springrestbackend.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String userId) {
        super("Cart with id " + userId + " not found.");
    }
}

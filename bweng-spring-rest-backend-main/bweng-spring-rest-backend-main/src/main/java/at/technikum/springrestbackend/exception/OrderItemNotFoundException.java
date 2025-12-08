package at.technikum.springrestbackend.exception;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(Integer itemId) {
        super("Order item with id " + itemId + " not found.");
    }
}

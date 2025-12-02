package at.technikum.springrestbackend.exception;

/**
 * Exception thrown when a product with a given id cannot be found.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Integer id) {
        super("Product with id " + id + " not found.");
    }
}

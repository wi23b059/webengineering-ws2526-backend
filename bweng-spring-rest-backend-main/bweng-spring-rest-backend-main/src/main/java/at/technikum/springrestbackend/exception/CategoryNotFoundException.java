package at.technikum.springrestbackend.exception;

/**
 * Exception thrown when a category with a given id cannot be found.
 */
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Integer id) {
        super("Category with id " + id + " not found.");
    }
}

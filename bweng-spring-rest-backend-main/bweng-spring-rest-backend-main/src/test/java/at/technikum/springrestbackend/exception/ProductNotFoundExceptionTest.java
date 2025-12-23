package at.technikum.springrestbackend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        Integer id = 99;

        ProductNotFoundException ex = new ProductNotFoundException(id);

        assertEquals("Product with id 99 not found.", ex.getMessage());
    }
}

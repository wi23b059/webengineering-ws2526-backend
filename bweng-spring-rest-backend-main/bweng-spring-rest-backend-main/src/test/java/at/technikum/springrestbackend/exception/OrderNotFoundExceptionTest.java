package at.technikum.springrestbackend.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        Integer id = 42;

        OrderNotFoundException ex = new OrderNotFoundException(id);

        assertEquals("Order with id 42 not found.", ex.getMessage());
    }
}

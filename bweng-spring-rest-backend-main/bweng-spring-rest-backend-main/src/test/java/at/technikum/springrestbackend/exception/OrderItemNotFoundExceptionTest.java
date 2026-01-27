package at.technikum.springrestbackend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link OrderItemNotFoundException}.
 */

class OrderItemNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        // given
        Integer itemId = 42;

        // when
        OrderItemNotFoundException exception =
                new OrderItemNotFoundException(itemId);

        // then
        assertNotNull(exception);
        assertEquals(
                "Order item with id 42 not found.",
                exception.getMessage()
        );
    }
}

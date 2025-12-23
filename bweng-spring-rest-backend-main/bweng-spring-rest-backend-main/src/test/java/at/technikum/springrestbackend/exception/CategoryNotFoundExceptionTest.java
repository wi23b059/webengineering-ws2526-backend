package at.technikum.springrestbackend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCorrectMessage() {
        // given
        Integer categoryId = 42;

        // when
        CategoryNotFoundException exception =
                new CategoryNotFoundException(categoryId);

        // then
        assertNotNull(exception);
        assertEquals(
                "Category with id 42 not found.",
                exception.getMessage()
        );
    }

    @Test
    void shouldBeRuntimeException() {
        // given
        CategoryNotFoundException exception =
                new CategoryNotFoundException(1);

        // then
        assertTrue(exception instanceof RuntimeException);
    }
}

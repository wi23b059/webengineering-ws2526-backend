package at.technikum.springrestbackend.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Cart}.
 */

class CartTest {

    @Test
    void prePersist_shouldSetCreatedAndUpdatedAt() {
        // given
        Cart cart = Cart.builder()
                .userId("user123")
                .productId(10)
                .quantity(2)
                .build();

        // when
        cart.prePersist();

        // then
        assertNotNull(cart.getCreatedAt(), "createdAt should be set");
        assertNotNull(cart.getUpdatedAt(), "updatedAt should be set");
        assertEquals(cart.getCreatedAt(), cart.getUpdatedAt(),
                "createdAt and updatedAt should be equal on persist");
    }

    @Test
    void preUpdate_shouldUpdateUpdatedAtOnly() throws InterruptedException {
        // given
        Cart cart = Cart.builder()
                .userId("user123")
                .productId(10)
                .quantity(2)
                .build();

        cart.prePersist();
        LocalDateTime createdAt = cart.getCreatedAt();

        // small delay to ensure time difference
        Thread.sleep(5);

        // when
        cart.preUpdate();

        // then
        assertEquals(createdAt, cart.getCreatedAt(),
                "createdAt should not change on update");
        assertTrue(cart.getUpdatedAt().isAfter(createdAt),
                "updatedAt should be newer than createdAt");
    }

    @Test
    void builder_shouldSetAllFieldsCorrectly() {
        // given
        LocalDateTime now = LocalDateTime.now();

        // when
        Cart cart = Cart.builder()
                .id(1)
                .userId("user123")
                .productId(99)
                .quantity(5)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // then
        assertEquals(1, cart.getId());
        assertEquals("user123", cart.getUserId());
        assertEquals(99, cart.getProductId());
        assertEquals(5, cart.getQuantity());
        assertEquals(now, cart.getCreatedAt());
        assertEquals(now, cart.getUpdatedAt());
    }
}

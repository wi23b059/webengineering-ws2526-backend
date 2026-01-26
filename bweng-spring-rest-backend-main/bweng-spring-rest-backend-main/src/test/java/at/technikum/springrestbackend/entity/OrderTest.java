package at.technikum.springrestbackend.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void builder_shouldSetAllFieldsCorrectly() {
        // given
        LocalDateTime now = LocalDateTime.now();

        // when
        Order order = Order.builder()
                .id(1)
                .userId("user123")
                .totalPrice(BigDecimal.valueOf(99.99))
                .status(Order.Status.PENDING)
                .createdAt(now)
                .paymentMethod("CREDIT_CARD")
                .invoiceNumber("INV-001")
                .build();

        // then
        assertEquals(1, order.getId());
        assertEquals("user123", order.getUserId());
        assertEquals(BigDecimal.valueOf(99.99), order.getTotalPrice());
        assertEquals(Order.Status.PENDING, order.getStatus());
        assertEquals(now, order.getCreatedAt());
        assertEquals("CREDIT_CARD", order.getPaymentMethod());
        assertEquals("INV-001", order.getInvoiceNumber());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // given
        Order order = new Order();

        // when
        order.setId(2);
        order.setUserId("user456");
        order.setTotalPrice(BigDecimal.TEN);
        order.setStatus(Order.Status.COMPLETED);
        order.setPaymentMethod("PAYPAL");
        order.setInvoiceNumber("INV-002");

        // then
        assertEquals(2, order.getId());
        assertEquals("user456", order.getUserId());
        assertEquals(BigDecimal.TEN, order.getTotalPrice());
        assertEquals(Order.Status.COMPLETED, order.getStatus());
        assertEquals("PAYPAL", order.getPaymentMethod());
        assertEquals("INV-002", order.getInvoiceNumber());
    }

    @Test
    void prePersist_shouldSetCreatedAt() {
        // given
        Order order = new Order();
        assertNull(order.getCreatedAt());

        // when
        order.prePersist();

        // then
        assertNotNull(order.getCreatedAt());
    }

    @Test
    void statusEnum_shouldContainExpectedValues() {
        // then
        assertNotNull(Order.Status.valueOf("PENDING"));
        assertNotNull(Order.Status.valueOf("COMPLETED"));
        assertNotNull(Order.Status.valueOf("CANCELED"));
    }
}

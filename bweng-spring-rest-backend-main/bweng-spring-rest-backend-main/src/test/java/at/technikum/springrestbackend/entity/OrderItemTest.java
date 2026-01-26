package at.technikum.springrestbackend.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void builder_shouldSetAllFieldsCorrectly() {
        // given
        LocalDateTime now = LocalDateTime.now();

        Order order = new Order();
        order.setId(100);

        // when
        OrderItem orderItem = OrderItem.builder()
                .id(1)
                .order(order)
                .productId(200)
                .quantity(3)
                .price(BigDecimal.valueOf(19.99))
                .createdAt(now)
                .build();

        // then
        assertEquals(1, orderItem.getId());
        assertEquals(100, orderItem.getOrder().getId());
        assertEquals(200, orderItem.getProductId());
        assertEquals(3, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(19.99), orderItem.getPrice());
        assertEquals(now, orderItem.getCreatedAt());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // given
        OrderItem orderItem = new OrderItem();
        Order order = new Order();
        order.setId(101);

        // when
        orderItem.setId(2);
        orderItem.setOrder(order);
        orderItem.setProductId(201);
        orderItem.setQuantity(5);
        orderItem.setPrice(BigDecimal.TEN);

        // then
        assertEquals(2, orderItem.getId());
        assertEquals(101, orderItem.getOrder().getId());
        assertEquals(201, orderItem.getProductId());
        assertEquals(5, orderItem.getQuantity());
        assertEquals(BigDecimal.TEN, orderItem.getPrice());
    }

    @Test
    void prePersist_shouldSetCreatedAt() {
        // given
        OrderItem orderItem = new OrderItem();
        assertNull(orderItem.getCreatedAt());

        // when
        orderItem.prePersist();

        // then
        assertNotNull(orderItem.getCreatedAt());
    }
}

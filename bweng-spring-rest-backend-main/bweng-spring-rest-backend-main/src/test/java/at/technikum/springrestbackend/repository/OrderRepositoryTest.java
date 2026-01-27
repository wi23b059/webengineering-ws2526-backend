package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link OrderRepository}.
 */

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void saveAndFindByUserId_shouldReturnOrdersForUser() {
        // given
        Order order1 = Order.builder()
                .userId("user1")
                .totalPrice(BigDecimal.valueOf(100))
                .status(Order.Status.PENDING)
                .build();

        Order order2 = Order.builder()
                .userId("user1")
                .totalPrice(BigDecimal.valueOf(50))
                .status(Order.Status.PENDING)
                .build();

        orderRepository.save(order1);
        orderRepository.save(order2);

        // when
        List<Order> orders = orderRepository.findByUserId("user1");

        // then
        assertEquals(2, orders.size());
        assertTrue(orders.stream().allMatch(o -> o.getUserId().equals("user1")));
    }

    @Test
    void findByUserId_shouldReturnEmptyListIfNoOrders() {
        // when
        List<Order> orders = orderRepository.findByUserId("nonexistent");

        // then
        assertTrue(orders.isEmpty());
    }
}

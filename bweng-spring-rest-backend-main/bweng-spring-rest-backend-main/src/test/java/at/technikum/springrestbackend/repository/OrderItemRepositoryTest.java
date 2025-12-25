package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void saveAndFindByOrderId_shouldReturnItemsForOrder() {
        // given
        OrderItem item1 = OrderItem.builder()
                .orderId(1)
                .productId(10)
                .quantity(2)
                .price(BigDecimal.valueOf(19.99))
                .build();

        OrderItem item2 = OrderItem.builder()
                .orderId(1)
                .productId(11)
                .quantity(1)
                .price(BigDecimal.valueOf(9.99))
                .build();

        orderItemRepository.save(item1);
        orderItemRepository.save(item2);

        // when
        List<OrderItem> result = orderItemRepository.findByOrderId(1);

        // then
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(i -> i.getOrderId().equals(1)));
    }

    @Test
    void findByOrderIdAndId_shouldReturnCorrectItem() {
        // given
        OrderItem item = OrderItem.builder()
                .orderId(2)
                .productId(20)
                .quantity(3)
                .price(BigDecimal.valueOf(29.99))
                .build();

        OrderItem saved = orderItemRepository.save(item);

        // when
        Optional<OrderItem> result =
                orderItemRepository.findByOrderIdAndId(2, saved.getId());

        // then
        assertTrue(result.isPresent());
        assertEquals(saved.getId(), result.get().getId());
        assertEquals(2, result.get().getOrderId());
    }

    @Test
    void findByOrderIdAndId_shouldReturnEmptyIfNotFound() {
        // when
        Optional<OrderItem> result =
                orderItemRepository.findByOrderIdAndId(999, 999);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteByOrderIdAndId_shouldRemoveItem() {
        // given
        OrderItem item = OrderItem.builder()
                .orderId(3)
                .productId(30)
                .quantity(1)
                .price(BigDecimal.valueOf(49.99))
                .build();

        OrderItem saved = orderItemRepository.save(item);

        // when
        orderItemRepository.deleteByOrderIdAndId(3, saved.getId());

        // then
        Optional<OrderItem> result =
                orderItemRepository.findByOrderIdAndId(3, saved.getId());

        assertTrue(result.isEmpty());
    }
}

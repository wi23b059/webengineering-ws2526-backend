package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Order;
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

    @Autowired
    private OrderRepository orderRepository;

    // -------------------------------------------------------------------------
    // findByOrder_Id
    // -------------------------------------------------------------------------

    @Test
    void saveAndFindByOrderId_shouldReturnItemsForOrder() {
        // given
        Order order = orderRepository.save(Order.builder().build());

        OrderItem item1 = OrderItem.builder()
                .order(order)
                .productId(10)
                .quantity(2)
                .price(BigDecimal.valueOf(19.99))
                .build();

        OrderItem item2 = OrderItem.builder()
                .order(order)
                .productId(11)
                .quantity(1)
                .price(BigDecimal.valueOf(9.99))
                .build();

        orderItemRepository.saveAll(List.of(item1, item2));

        // when
        List<OrderItem> result =
                orderItemRepository.findByOrder_Id(order.getId());

        // then
        assertEquals(2, result.size());
        assertTrue(result.stream()
                .allMatch(i -> i.getOrder().getId().equals(order.getId())));
    }

    // -------------------------------------------------------------------------
    // findByOrder_IdAndId
    // -------------------------------------------------------------------------

    @Test
    void findByOrderIdAndId_shouldReturnCorrectItem() {
        // given
        Order order = orderRepository.save(Order.builder().build());

        OrderItem item = OrderItem.builder()
                .order(order)
                .productId(20)
                .quantity(3)
                .price(BigDecimal.valueOf(29.99))
                .build();

        OrderItem saved = orderItemRepository.save(item);

        // when
        Optional<OrderItem> result =
                orderItemRepository.findByOrder_IdAndId(
                        order.getId(),
                        saved.getId()
                );

        // then
        assertTrue(result.isPresent());
        assertEquals(saved.getId(), result.get().getId());
        assertEquals(order.getId(), result.get().getOrder().getId());
    }

    @Test
    void findByOrderIdAndId_shouldReturnEmptyIfNotFound() {
        // when
        Optional<OrderItem> result =
                orderItemRepository.findByOrder_IdAndId(999, 999);

        // then
        assertTrue(result.isEmpty());
    }

    // -------------------------------------------------------------------------
    // deleteByOrder_IdAndId
    // -------------------------------------------------------------------------

    @Test
    void deleteByOrderIdAndId_shouldRemoveItem() {
        // given
        Order order = orderRepository.save(Order.builder().build());

        OrderItem item = OrderItem.builder()
                .order(order)
                .productId(30)
                .quantity(1)
                .price(BigDecimal.valueOf(49.99))
                .build();

        OrderItem saved = orderItemRepository.save(item);

        // when
        orderItemRepository.deleteByOrder_IdAndId(
                order.getId(),
                saved.getId()
        );
        orderItemRepository.flush();

        // then
        Optional<OrderItem> result =
                orderItemRepository.findByOrder_IdAndId(
                        order.getId(),
                        saved.getId()
                );

        assertTrue(result.isEmpty());
    }
}

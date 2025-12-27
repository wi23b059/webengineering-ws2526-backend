package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.entity.OrderItem;
import at.technikum.springrestbackend.exception.OrderItemNotFoundException;
import at.technikum.springrestbackend.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void getAllOrderItems_shouldReturnList() {
        OrderItem item = new OrderItem();
        item.setId(1);
        item.setOrderId(10);
        item.setProductId(100);
        item.setQuantity(2);
        item.setPrice(BigDecimal.valueOf(9.99));

        when(orderItemRepository.findByOrderId(10))
                .thenReturn(List.of(item));

        var result = orderItemService.getAllOrderItems(10);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProductId()).isEqualTo(100);
    }

    @Test
    void getOrderItem_shouldReturnItem() {
        OrderItem item = new OrderItem();
        item.setId(1);
        item.setOrderId(10);

        when(orderItemRepository.findByOrderIdAndId(10, 1))
                .thenReturn(Optional.of(item));

        var result = orderItemService.getOrderItem(10, 1);

        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void getOrderItem_shouldThrowException_whenNotFound() {
        when(orderItemRepository.findByOrderIdAndId(10, 1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderItemService.getOrderItem(10, 1))
                .isInstanceOf(OrderItemNotFoundException.class);
    }

    @Test
    void addOrderItem_shouldSaveAndReturnItem() {
        OrderItemRequestDto dto = new OrderItemRequestDto();
        dto.setProductId(100);
        dto.setQuantity(3);
        dto.setPrice(BigDecimal.valueOf(5.99));

        OrderItem saved = new OrderItem();
        saved.setId(1);
        saved.setOrderId(10);
        saved.setProductId(100);
        saved.setQuantity(3);
        saved.setPrice(BigDecimal.valueOf(5.99));

        when(orderItemRepository.save(any(OrderItem.class)))
                .thenReturn(saved);

        var result = orderItemService.addOrderItem(10, dto);

        assertThat(result.getId()).isEqualTo(1);
        verify(orderItemRepository).save(any(OrderItem.class));
    }

    @Test
    void updateOrderItem_shouldUpdateExistingItem() {
        OrderItem existing = new OrderItem();
        existing.setId(1);
        existing.setOrderId(10);

        OrderItemRequestDto dto = new OrderItemRequestDto();
        dto.setProductId(200);
        dto.setQuantity(5);
        dto.setPrice(BigDecimal.valueOf(19.99));

        when(orderItemRepository.findByOrderIdAndId(10, 1))
                .thenReturn(Optional.of(existing));
        when(orderItemRepository.save(existing))
                .thenReturn(existing);

        var result = orderItemService.updateOrderItem(10, 1, dto);

        assertThat(result.getProductId()).isEqualTo(200);
        verify(orderItemRepository).save(existing);
    }

    @Test
    void deleteOrderItem_shouldCallRepository() {
        orderItemService.deleteOrderItem(10, 1);

        verify(orderItemRepository).deleteByOrderIdAndId(10, 1);
    }
}

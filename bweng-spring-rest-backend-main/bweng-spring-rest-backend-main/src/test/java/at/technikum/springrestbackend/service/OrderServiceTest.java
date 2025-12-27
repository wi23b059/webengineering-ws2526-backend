package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.OrderRequestDto;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.exception.OrderNotFoundException;
import at.technikum.springrestbackend.repository.OrderRepository;
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
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrders_shouldReturnList() {
        Order order = new Order();
        order.setId(1);

        when(orderRepository.findAll())
                .thenReturn(List.of(order));

        var result = orderService.getAllOrders();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
    }

    @Test
    void getOrder_shouldReturnOrder() {
        Order order = new Order();
        order.setId(1);

        when(orderRepository.findById(1))
                .thenReturn(Optional.of(order));

        var result = orderService.getOrder(1);

        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void getOrder_shouldThrowException_whenNotFound() {
        when(orderRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrder(1))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void createOrder_shouldSaveAndReturnOrder() {
        OrderRequestDto dto = new OrderRequestDto();
        dto.setUserId("10");
        dto.setTotalPrice(BigDecimal.valueOf(49.99));
        dto.setPaymentMethod("CREDIT_CARD");

        Order saved = new Order();
        saved.setId(1);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(saved);

        var result = orderService.createOrder(dto);

        assertThat(result.getId()).isEqualTo(1);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrder_shouldUpdateExistingOrder() {
        Order existing = new Order();
        existing.setId(1);

        OrderRequestDto dto = new OrderRequestDto();
        dto.setTotalPrice(BigDecimal.valueOf(99.99));
        dto.setPaymentMethod("PAYPAL");

        when(orderRepository.findById(1))
                .thenReturn(Optional.of(existing));
        when(orderRepository.save(existing))
                .thenReturn(existing);

        var result = orderService.updateOrder(1, dto);

        assertThat(result.getTotalPrice()).isEqualTo(BigDecimal.valueOf(99.99));
        verify(orderRepository).save(existing);
    }

    @Test
    void updateOrder_shouldThrowException_whenNotFound() {
        when(orderRepository.findById(1))
                .thenReturn(Optional.empty());

        OrderRequestDto dto = new OrderRequestDto();

        assertThatThrownBy(() -> orderService.updateOrder(1, dto))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void deleteOrder_shouldDeleteOrder() {
        when(orderRepository.existsById(1))
                .thenReturn(true);

        orderService.deleteOrder(1);

        verify(orderRepository).deleteById(1);
    }

    @Test
    void deleteOrder_shouldThrowException_whenNotFound() {
        when(orderRepository.existsById(1))
                .thenReturn(false);

        assertThatThrownBy(() -> orderService.deleteOrder(1))
                .isInstanceOf(OrderNotFoundException.class);
    }
}

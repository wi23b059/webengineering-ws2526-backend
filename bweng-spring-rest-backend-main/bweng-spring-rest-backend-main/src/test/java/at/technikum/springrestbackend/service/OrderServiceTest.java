package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.OrderRequestDto;
import at.technikum.springrestbackend.dto.OrderResponseDto;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.exception.OrderNotFoundException;
import at.technikum.springrestbackend.mapper.OrderMapper;
import at.technikum.springrestbackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void getAllOrders_returnsAllOrders() {
        Order order = new Order();
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderResponseDto> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getOrder_existingId_returnsOrder() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        OrderResponseDto dto = orderService.getOrder(1);

        assertEquals(1, dto.getId());
        verify(orderRepository).findById(1);
    }

    @Test
    void getOrder_nonExistingId_throwsException() {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(99));
    }

    @Test
    void getOrdersByUser_returnsUserOrders() {
        Order order = new Order();
        order.setUserId("user1");
        when(orderRepository.findByUserId("user1")).thenReturn(List.of(order));

        List<OrderResponseDto> result = orderService.getOrdersByUser("user1");

        assertEquals(1, result.size());
        verify(orderRepository).findByUserId("user1");
    }

    @Test
    void createOrder_savesAndReturnsOrder() {
        OrderRequestDto dto = new OrderRequestDto();
        dto.setUserId("user1");
        dto.setTotalPrice(BigDecimal.TEN);
        dto.setPaymentMethod("INVOICE");

        Order savedOrder = OrderMapper.toEntity(dto);
        savedOrder.setId(1);

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponseDto response = orderService.createOrder(dto);

        assertEquals(1, response.getId());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrder_existingOrder_updatesAndReturns() {
        Order existing = new Order();
        existing.setId(1);
        existing.setTotalPrice(BigDecimal.ONE);
        when(orderRepository.findById(1)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        OrderRequestDto dto = new OrderRequestDto();
        dto.setTotalPrice(BigDecimal.TEN);
        dto.setPaymentMethod("CARD");

        OrderResponseDto updated = orderService.updateOrder(1, dto);

        assertEquals(BigDecimal.TEN, updated.getTotalPrice());
        assertEquals("CARD", updated.getPaymentMethod());
    }

    @Test
    void updateOrder_nonExisting_throwsException() {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());
        OrderRequestDto dto = new OrderRequestDto();
        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(99, dto));
    }

    @Test
    void deleteOrder_existingOrder_deletes() {
        when(orderRepository.existsById(1)).thenReturn(true);

        orderService.deleteOrder(1);

        verify(orderRepository).deleteById(1);
    }

    @Test
    void deleteOrder_nonExisting_throwsException() {
        when(orderRepository.existsById(99)).thenReturn(false);
        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(99));
    }

    @Test
    void updateStatus_validStatus_updatesAndReturns() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponseDto updated = orderService.updateStatus(1, "completed");

        assertEquals("COMPLETED", updated.getStatus());
    }

    @Test
    void updateStatus_invalidStatus_throwsException() {
        Order order = new Order();
        order.setId(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        assertThrows(IllegalArgumentException.class, () -> orderService.updateStatus(1, "invalid"));
    }

    @Test
    void updateStatus_nonExistingOrder_throwsException() {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> orderService.updateStatus(99, "completed"));
    }
}

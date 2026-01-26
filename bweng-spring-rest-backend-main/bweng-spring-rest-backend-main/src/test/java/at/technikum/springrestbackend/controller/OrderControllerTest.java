package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.OrderRequestDto;
import at.technikum.springrestbackend.dto.OrderResponseDto;
import at.technikum.springrestbackend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private OrderResponseDto sampleOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleOrder = OrderResponseDto.builder()
                .id(1)
                .userId("user-123")
                .totalPrice(BigDecimal.valueOf(100))
                .status("PENDING")
                .build();
    }

    @Test
    void testGetAllOrders() {
        when(orderService.getAllOrders()).thenReturn(List.of(sampleOrder));

        List<OrderResponseDto> result = orderController.getAllOrders();

        assertEquals(1, result.size());
        assertEquals(sampleOrder.getId(), result.getFirst().getId());
        verify(orderService).getAllOrders();
    }

    @Test
    void testGetOrder() {
        when(orderService.getOrder(1)).thenReturn(sampleOrder);

        OrderResponseDto result = orderController.getOrder(1);

        assertEquals(sampleOrder.getId(), result.getId());
        verify(orderService).getOrder(1);
    }

    @Test
    void testGetOrdersByUser() {
        when(orderService.getOrdersByUser("user-123")).thenReturn(List.of(sampleOrder));

        List<OrderResponseDto> result = orderController.getOrdersByUser("user-123");

        assertEquals(1, result.size());
        verify(orderService).getOrdersByUser("user-123");
    }

    @Test
    void testCreateOrder() {
        OrderRequestDto dto = OrderRequestDto.builder()
                .userId("user-123")
                .totalPrice(BigDecimal.valueOf(100))
                .build();

        when(orderService.createOrder(dto)).thenReturn(sampleOrder);

        OrderResponseDto result = orderController.createOrder(dto);

        assertEquals(sampleOrder.getId(), result.getId());
        verify(orderService).createOrder(dto);
    }

    @Test
    void testUpdateOrder() {
        OrderRequestDto dto = OrderRequestDto.builder()
                .userId("user-123")
                .totalPrice(BigDecimal.valueOf(150))
                .build();

        OrderResponseDto updatedOrder = OrderResponseDto.builder()
                .id(1)
                .userId("user-123")
                .totalPrice(BigDecimal.valueOf(150))
                .status("PENDING")
                .build();

        when(orderService.updateOrder(1, dto)).thenReturn(updatedOrder);

        OrderResponseDto result = orderController.updateOrder(1, dto);

        assertEquals(BigDecimal.valueOf(150), result.getTotalPrice());
        verify(orderService).updateOrder(1, dto);
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderService).deleteOrder(1);

        ResponseEntity<Void> response = orderController.deleteOrder(1);

        assertEquals(204, response.getStatusCode().value());
        verify(orderService).deleteOrder(1);
    }

    @Test
    void testUpdateOrderStatus() {
        OrderResponseDto updatedOrder = OrderResponseDto.builder()
                .id(1)
                .userId("user-123")
                .totalPrice(BigDecimal.valueOf(100))
                .status("COMPLETED")
                .build();

        when(orderService.updateStatus(1, "completed")).thenReturn(updatedOrder);

        ResponseEntity<OrderResponseDto> response = orderController.updateOrderStatus(1, "completed");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("COMPLETED", response.getBody().getStatus());
        verify(orderService).updateStatus(1, "completed");
    }
}

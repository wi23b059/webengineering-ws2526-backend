package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.dto.OrderItemResponseDto;
import at.technikum.springrestbackend.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link OrderItemController}.
 */

class OrderItemControllerTest {

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderItemController orderItemController;

    private OrderItemResponseDto sampleItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleItem = OrderItemResponseDto.builder()
                .id(1)
                .orderId(1)
                .productId(10)
                .quantity(2)
                .price(BigDecimal.valueOf(50))
                .createdAt(null)
                .build();
    }

    @Test
    void testGetAllOrderItems() {
        when(orderItemService.getAllOrderItems(1)).thenReturn(List.of(sampleItem));

        List<OrderItemResponseDto> result = orderItemController.getAllOrderItems(1);

        assertEquals(1, result.size());
        assertEquals(sampleItem.getId(), result.getFirst().getId());
        verify(orderItemService).getAllOrderItems(1);
    }

    @Test
    void testGetOrderItem() {
        when(orderItemService.getOrderItem(1, 1)).thenReturn(sampleItem);

        OrderItemResponseDto result = orderItemController.getOrderItem(1, 1);

        assertEquals(sampleItem.getId(), result.getId());
        verify(orderItemService).getOrderItem(1, 1);
    }

    @Test
    void testAddOrderItem() {
        OrderItemRequestDto requestDto = OrderItemRequestDto.builder()
                .productId(10)
                .quantity(2)
                .build();

        when(orderItemService.addOrderItem(1, requestDto)).thenReturn(sampleItem);

        OrderItemResponseDto result = orderItemController.addOrderItem(1, requestDto);

        assertEquals(sampleItem.getId(), result.getId());
        verify(orderItemService).addOrderItem(1, requestDto);
    }

    @Test
    void testUpdateOrderItem() {
        OrderItemRequestDto requestDto = OrderItemRequestDto.builder()
                .productId(10)
                .quantity(3)
                .build();

        OrderItemResponseDto updatedItem = OrderItemResponseDto.builder()
                .id(1)
                .orderId(1)
                .productId(10)
                .quantity(3)
                .price(BigDecimal.valueOf(50))
                .createdAt(null)
                .build();

        when(orderItemService.updateOrderItem(1, 1, requestDto)).thenReturn(updatedItem);

        OrderItemResponseDto result = orderItemController.updateOrderItem(1, 1, requestDto);

        assertEquals(3, result.getQuantity());
        verify(orderItemService).updateOrderItem(1, 1, requestDto);
    }

    @Test
    void testDeleteOrderItem() {
        doNothing().when(orderItemService).deleteOrderItem(1, 1);

        ResponseEntity<Void> response = orderItemController.deleteOrderItem(1, 1);

        assertEquals(204, response.getStatusCode().value());
        verify(orderItemService).deleteOrderItem(1, 1);
    }
}

package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.dto.OrderItemResponseDto;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.entity.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemMapperTest {

    @Test
    void toEntity_shouldMapOrderAndRequestDtoToOrderItemEntity() {
        // given
        Order order = Order.builder()
                .id(42)
                .build();

        OrderItemRequestDto dto = OrderItemRequestDto.builder()
                .productId(7)
                .quantity(3)
                .price(new BigDecimal("19.99"))
                .build();

        // when
        OrderItem orderItem = OrderItemMapper.toEntity(order, dto);

        // then
        assertNotNull(orderItem);
        assertEquals(order, orderItem.getOrder());
        assertEquals(7, orderItem.getProductId());
        assertEquals(3, orderItem.getQuantity());
        assertEquals(new BigDecimal("19.99"), orderItem.getPrice());
    }

    @Test
    void toResponseDto_shouldMapOrderItemEntityToResponseDto() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();

        Order order = Order.builder()
                .id(42)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .id(5)
                .order(order)
                .productId(7)
                .quantity(3)
                .price(new BigDecimal("19.99"))
                .createdAt(createdAt)
                .build();

        // when
        OrderItemResponseDto responseDto = OrderItemMapper.toResponseDto(orderItem);

        // then
        assertNotNull(responseDto);
        assertEquals(5, responseDto.getId());
        assertEquals(42, responseDto.getOrderId());
        assertEquals(7, responseDto.getProductId());
        assertEquals(3, responseDto.getQuantity());
        assertEquals(new BigDecimal("19.99"), responseDto.getPrice());
        assertEquals(createdAt, responseDto.getCreatedAt());
    }
}

package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.dto.OrderItemResponseDto;
import at.technikum.springrestbackend.entity.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemMapperTest {

    @Test
    void toEntity_shouldMapOrderIdAndRequestDtoToOrderItemEntity() {
        // given
        Integer orderId = 42;
        OrderItemRequestDto dto = OrderItemRequestDto.builder()
                .productId(7)
                .quantity(3)
                .price(new BigDecimal("19.99"))
                .build();

        // when
        OrderItem orderItem = OrderItemMapper.toEntity(orderId, dto);

        // then
        assertNotNull(orderItem);
        assertEquals(orderId, orderItem.getOrderId());
        assertEquals(7, orderItem.getProductId());
        assertEquals(3, orderItem.getQuantity());
        assertEquals(new BigDecimal("19.99"), orderItem.getPrice());
    }

    @Test
    void toResponseDto_shouldMapOrderItemEntityToResponseDto() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();

        OrderItem orderItem = OrderItem.builder()
                .id(5)
                .orderId(42)
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

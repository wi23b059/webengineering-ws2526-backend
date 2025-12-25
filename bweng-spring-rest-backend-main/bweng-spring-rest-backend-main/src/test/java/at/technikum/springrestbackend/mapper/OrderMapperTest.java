package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.OrderRequestDto;
import at.technikum.springrestbackend.dto.OrderResponseDto;
import at.technikum.springrestbackend.entity.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    @Test
    void toEntity_shouldMapOrderRequestDtoToOrderEntity() {
        // given
        OrderRequestDto dto = OrderRequestDto.builder()
                .userId("user-123")
                .totalPrice(new BigDecimal("99.99"))
                .paymentMethod("CREDIT_CARD")
                .build();

        // when
        Order order = OrderMapper.toEntity(dto);

        // then
        assertNotNull(order);
        assertEquals("user-123", order.getUserId());
        assertEquals(new BigDecimal("99.99"), order.getTotalPrice());
        assertEquals("CREDIT_CARD", order.getPaymentMethod());
        assertEquals(Order.Status.pending, order.getStatus());
    }

    @Test
    void toResponseDto_shouldMapOrderEntityToOrderResponseDto() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();

        Order order = Order.builder()
                .id(10)
                .userId("user-123")
                .totalPrice(new BigDecimal("99.99"))
                .status(Order.Status.completed)
                .createdAt(createdAt)
                .paymentMethod("PAYPAL")
                .invoiceNumber("INV-2024-001")
                .build();

        // when
        OrderResponseDto responseDto = OrderMapper.toResponseDto(order);

        // then
        assertNotNull(responseDto);
        assertEquals(10, responseDto.getId());
        assertEquals("user-123", responseDto.getUserId());
        assertEquals(new BigDecimal("99.99"), responseDto.getTotalPrice());
        assertEquals("completed", responseDto.getStatus());
        assertEquals(createdAt, responseDto.getCreatedAt());
        assertEquals("PAYPAL", responseDto.getPaymentMethod());
        assertEquals("INV-2024-001", responseDto.getInvoiceNumber());
    }
}

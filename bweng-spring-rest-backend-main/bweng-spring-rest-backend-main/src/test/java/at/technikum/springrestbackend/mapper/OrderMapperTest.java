package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.OrderRequestDto;
import at.technikum.springrestbackend.dto.OrderResponseDto;
import at.technikum.springrestbackend.entity.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    @Test
    void testToEntity() {
        UUID userId = UUID.randomUUID();

        // DTO hat userId als String, nicht UUID
        OrderRequestDto dto = OrderRequestDto.builder()
                .userId(userId.toString()) // Korrektur: String statt UUID
                .totalPrice(new BigDecimal("123.45"))
                .paymentMethod("CREDIT_CARD")
                .build();

        Order entity = OrderMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(userId.toString(), entity.getUserId()); // Entity hat UUID
        assertEquals(new BigDecimal("123.45"), entity.getTotalPrice());
        assertEquals("CREDIT_CARD", entity.getPaymentMethod());
        assertEquals(Order.Status.PENDING, entity.getStatus());
    }

    @Test
    void testToResponseDtoWithItems() {
        UUID userId = UUID.randomUUID();

        Order order = Order.builder()
                .id(1)
                .userId(userId.toString())
                .totalPrice(new BigDecimal("200.00"))
                .status(Order.Status.PENDING)
                .paymentMethod("PAYPAL")
                .invoiceNumber("INV-001")
                .build();

        order.setOrderItems(Collections.emptyList()); // Leere Liste statt null

        OrderResponseDto dto = OrderMapper.toResponseDto(order);

        assertNotNull(dto);
        assertEquals(order.getId(), dto.getId());
        assertEquals(userId.toString(), dto.getUserId()); // DTO hat String userId
        assertEquals(order.getTotalPrice(), dto.getTotalPrice());
        assertEquals(order.getStatus().name(), dto.getStatus());
        assertEquals(order.getPaymentMethod(), dto.getPaymentMethod());
        assertEquals(order.getInvoiceNumber(), dto.getInvoiceNumber());
        assertNotNull(dto.getItems());
        assertTrue(dto.getItems().isEmpty());
    }
}

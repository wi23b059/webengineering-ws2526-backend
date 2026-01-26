package at.technikum.springrestbackend.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseDtoTest {

    @Test
    void constructor_shouldSetAllFieldsCorrectly() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();

        // when
        OrderResponseDto dto = new OrderResponseDto(
                1,                          // id
                "user123",                  // userId
                BigDecimal.valueOf(99.99),  // totalPrice
                "PENDING",                  // status
                createdAt,                  // createdAt
                "PAYPAL",                   // paymentMethod
                "INV-001",                  // invoiceNumber
                List.of()                   // items
        );

        // then
        assertEquals(1, dto.getId());
        assertEquals("user123", dto.getUserId());
        assertEquals(BigDecimal.valueOf(99.99), dto.getTotalPrice());
        assertEquals("PENDING", dto.getStatus());
        assertEquals(createdAt, dto.getCreatedAt());
        assertEquals("PAYPAL", dto.getPaymentMethod());
        assertEquals("INV-001", dto.getInvoiceNumber());
        assertTrue(dto.getItems().isEmpty());
    }

    @Test
    void builder_shouldSetAllFieldsCorrectly() {
        // given
        LocalDateTime createdAt = LocalDateTime.now();

        // when
        OrderResponseDto dto = OrderResponseDto.builder()
                .id(1)
                .userId("user123")
                .totalPrice(BigDecimal.valueOf(99.99))
                .status("PENDING")
                .createdAt(createdAt)
                .paymentMethod("PAYPAL")
                .invoiceNumber("INV-001")
                .items(List.of())
                .build();

        // then
        assertEquals(1, dto.getId());
        assertEquals("user123", dto.getUserId());
        assertEquals(BigDecimal.valueOf(99.99), dto.getTotalPrice());
        assertEquals("PENDING", dto.getStatus());
        assertEquals(createdAt, dto.getCreatedAt());
        assertEquals("PAYPAL", dto.getPaymentMethod());
        assertEquals("INV-001", dto.getInvoiceNumber());
        assertTrue(dto.getItems().isEmpty());
    }
}

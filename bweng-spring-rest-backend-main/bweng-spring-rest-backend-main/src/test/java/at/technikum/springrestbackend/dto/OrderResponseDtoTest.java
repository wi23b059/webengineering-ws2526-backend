package at.technikum.springrestbackend.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link OrderResponseDto}.
 */
class OrderResponseDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void getterAndSetterWorkCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(1);
        dto.setUserId("user-123");
        dto.setTotalPrice(BigDecimal.valueOf(99.90));
        dto.setStatus("PAID");
        dto.setCreatedAt(now);
        dto.setPaymentMethod("CREDIT_CARD");
        dto.setInvoiceNumber("INV-2024-001");

        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getUserId()).isEqualTo("user-123");
        assertThat(dto.getTotalPrice()).isEqualTo(BigDecimal.valueOf(99.90));
        assertThat(dto.getStatus()).isEqualTo("PAID");
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getPaymentMethod()).isEqualTo("CREDIT_CARD");
        assertThat(dto.getInvoiceNumber()).isEqualTo("INV-2024-001");
    }

    @Test
    void allArgsConstructorCreatesCorrectObject() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 5, 20, 14, 30);

        OrderResponseDto dto = new OrderResponseDto(
                2,
                "user-42",
                BigDecimal.valueOf(120.50),
                "PENDING",
                timestamp,
                "PAYPAL",
                "INV-2024-002"
        );

        assertThat(dto.getId()).isEqualTo(2);
        assertThat(dto.getUserId()).isEqualTo("user-42");
        assertThat(dto.getTotalPrice()).isEqualTo(BigDecimal.valueOf(120.50));
        assertThat(dto.getStatus()).isEqualTo("PENDING");
        assertThat(dto.getCreatedAt()).isEqualTo(timestamp);
        assertThat(dto.getPaymentMethod()).isEqualTo("PAYPAL");
        assertThat(dto.getInvoiceNumber()).isEqualTo("INV-2024-002");
    }

    @Test
    void builderCreatesCorrectObject() {
        LocalDateTime timestamp = LocalDateTime.now();

        OrderResponseDto dto = OrderResponseDto.builder()
                .id(5)
                .userId("builder-user")
                .totalPrice(BigDecimal.valueOf(59.99))
                .status("SHIPPED")
                .createdAt(timestamp)
                .paymentMethod("CASH")
                .invoiceNumber("INV-2024-010")
                .build();

        assertThat(dto.getId()).isEqualTo(5);
        assertThat(dto.getUserId()).isEqualTo("builder-user");
        assertThat(dto.getTotalPrice()).isEqualTo(BigDecimal.valueOf(59.99));
        assertThat(dto.getStatus()).isEqualTo("SHIPPED");
        assertThat(dto.getCreatedAt()).isEqualTo(timestamp);
        assertThat(dto.getPaymentMethod()).isEqualTo("CASH");
        assertThat(dto.getInvoiceNumber()).isEqualTo("INV-2024-010");
    }

    // -------------------------------------------------------------------------
    // JSON Serialization
    // -------------------------------------------------------------------------

    @Test
    void serializesToJsonCorrectly() throws Exception {
        LocalDateTime timestamp = LocalDateTime.of(2024, 6, 1, 10, 15, 30);

        OrderResponseDto dto = OrderResponseDto.builder()
                .id(7)
                .userId("json-user")
                .totalPrice(BigDecimal.valueOf(150.75))
                .status("PAID")
                .createdAt(timestamp)
                .paymentMethod("CARD")
                .invoiceNumber("INV-2024-100")
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"id\":7");
        assertThat(json).contains("\"userId\":\"json-user\"");
        assertThat(json).contains("\"totalPrice\":150.75");
        assertThat(json).contains("\"status\":\"PAID\"");
        assertThat(json).contains("\"createdAt\"");
        assertThat(json).contains("\"paymentMethod\":\"CARD\"");
        assertThat(json).contains("\"invoiceNumber\":\"INV-2024-100\"");
    }

    @Test
    void deserializesFromJsonCorrectly() throws Exception {
        String json = """
                {
                  "id": 10,
                  "userId": "user-10",
                  "totalPrice": 200.00,
                  "status": "DELIVERED",
                  "createdAt": "2024-07-01T12:00:00",
                  "paymentMethod": "PAYPAL",
                  "invoiceNumber": "INV-2024-200"
                }
                """;

        OrderResponseDto dto = objectMapper.readValue(json, OrderResponseDto.class);

        assertThat(dto.getId()).isEqualTo(10);
        assertThat(dto.getUserId()).isEqualTo("user-10");
        assertThat(dto.getTotalPrice()).isEqualTo(BigDecimal.valueOf(200.00));
        assertThat(dto.getStatus()).isEqualTo("DELIVERED");
        assertThat(dto.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 7, 1, 12, 0, 0));
        assertThat(dto.getPaymentMethod()).isEqualTo("PAYPAL");
        assertThat(dto.getInvoiceNumber()).isEqualTo("INV-2024-200");
    }
}

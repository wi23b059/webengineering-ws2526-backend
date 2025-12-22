package at.technikum.springrestbackend.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link OrderItemResponseDto}.
 */
class OrderItemResponseDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void getterAndSetterWorkCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setId(1);
        dto.setOrderId(10);
        dto.setProductId(5);
        dto.setQuantity(3);
        dto.setPrice(BigDecimal.valueOf(19.99));
        dto.setCreatedAt(now);

        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getOrderId()).isEqualTo(10);
        assertThat(dto.getProductId()).isEqualTo(5);
        assertThat(dto.getQuantity()).isEqualTo(3);
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(19.99));
        assertThat(dto.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void allArgsConstructorCreatesCorrectObject() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 12, 0);

        OrderItemResponseDto dto = new OrderItemResponseDto(
                1,
                2,
                3,
                4,
                BigDecimal.valueOf(9.99),
                timestamp
        );

        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getOrderId()).isEqualTo(2);
        assertThat(dto.getProductId()).isEqualTo(3);
        assertThat(dto.getQuantity()).isEqualTo(4);
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(9.99));
        assertThat(dto.getCreatedAt()).isEqualTo(timestamp);
    }

    @Test
    void builderCreatesCorrectObject() {
        LocalDateTime timestamp = LocalDateTime.now();

        OrderItemResponseDto dto = OrderItemResponseDto.builder()
                .id(7)
                .orderId(8)
                .productId(9)
                .quantity(1)
                .price(BigDecimal.valueOf(29.90))
                .createdAt(timestamp)
                .build();

        assertThat(dto.getId()).isEqualTo(7);
        assertThat(dto.getOrderId()).isEqualTo(8);
        assertThat(dto.getProductId()).isEqualTo(9);
        assertThat(dto.getQuantity()).isEqualTo(1);
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(29.90));
        assertThat(dto.getCreatedAt()).isEqualTo(timestamp);
    }

    // -------------------------------------------------------------------------
    // JSON Serialization
    // -------------------------------------------------------------------------

    @Test
    void serializesToJsonCorrectly() throws Exception {
        LocalDateTime timestamp = LocalDateTime.of(2024, 5, 20, 14, 30);

        OrderItemResponseDto dto = OrderItemResponseDto.builder()
                .id(1)
                .orderId(2)
                .productId(3)
                .quantity(2)
                .price(BigDecimal.valueOf(15.50))
                .createdAt(timestamp)
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"orderId\":2");
        assertThat(json).contains("\"productId\":3");
        assertThat(json).contains("\"quantity\":2");
        assertThat(json).contains("\"price\":15.50");
        assertThat(json).contains("\"createdAt\"");
    }

    @Test
    void deserializesFromJsonCorrectly() throws Exception {
        String json = """
                {
                  "id": 5,
                  "orderId": 6,
                  "productId": 7,
                  "quantity": 3,
                  "price": 49.99,
                  "createdAt": "2024-06-01T10:15:30"
                }
                """;

        OrderItemResponseDto dto =
                objectMapper.readValue(json, OrderItemResponseDto.class);

        assertThat(dto.getId()).isEqualTo(5);
        assertThat(dto.getOrderId()).isEqualTo(6);
        assertThat(dto.getProductId()).isEqualTo(7);
        assertThat(dto.getQuantity()).isEqualTo(3);
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(49.99));
        assertThat(dto.getCreatedAt())
                .isEqualTo(LocalDateTime.of(2024, 6, 1, 10, 15, 30));
    }
}

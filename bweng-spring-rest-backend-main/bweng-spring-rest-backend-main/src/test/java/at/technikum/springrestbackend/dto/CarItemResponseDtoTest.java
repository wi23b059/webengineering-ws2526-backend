package at.technikum.springrestbackend.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link CartItemResponseDto}.
 */
class CartItemResponseDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void getterAndSetterWorkCorrectly() {
        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setId(1);
        dto.setUserId("user-123");
        dto.setProductId(42);
        dto.setQuantity(3);

        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getUserId()).isEqualTo("user-123");
        assertThat(dto.getProductId()).isEqualTo(42);
        assertThat(dto.getQuantity()).isEqualTo(3);
    }

    @Test
    void allArgsConstructorCreatesCorrectObject() {
        CartItemResponseDto dto = new CartItemResponseDto(
                1,
                "user-123",
                10,
                5
        );

        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getUserId()).isEqualTo("user-123");
        assertThat(dto.getProductId()).isEqualTo(10);
        assertThat(dto.getQuantity()).isEqualTo(5);
    }

    @Test
    void builderCreatesCorrectObject() {
        CartItemResponseDto dto = CartItemResponseDto.builder()
                .id(99)
                .userId("abc")
                .productId(7)
                .quantity(2)
                .build();

        assertThat(dto.getId()).isEqualTo(99);
        assertThat(dto.getUserId()).isEqualTo("abc");
        assertThat(dto.getProductId()).isEqualTo(7);
        assertThat(dto.getQuantity()).isEqualTo(2);
    }

    // -------------------------------------------------------------------------
    // JSON Serialization
    // -------------------------------------------------------------------------

    @Test
    void serializesToJsonCorrectly() throws Exception {
        CartItemResponseDto dto = CartItemResponseDto.builder()
                .id(1)
                .userId("user-1")
                .productId(5)
                .quantity(4)
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"userId\":\"user-1\"");
        assertThat(json).contains("\"productId\":5");
        assertThat(json).contains("\"quantity\":4");
    }

    @Test
    void deserializesFromJsonCorrectly() throws Exception {
        String json = """
                {
                  "id": 3,
                  "userId": "user-x",
                  "productId": 8,
                  "quantity": 6
                }
                """;

        CartItemResponseDto dto =
                objectMapper.readValue(json, CartItemResponseDto.class);

        assertThat(dto.getId()).isEqualTo(3);
        assertThat(dto.getUserId()).isEqualTo("user-x");
        assertThat(dto.getProductId()).isEqualTo(8);
        assertThat(dto.getQuantity()).isEqualTo(6);
    }
}

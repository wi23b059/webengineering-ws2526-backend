package at.technikum.springrestbackend.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link CategoryResponseDto}.
 */
class CategoryResponseDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void getterAndSetterWorkCorrectly() {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(1);
        dto.setName("Books");

        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getName()).isEqualTo("Books");
    }

    @Test
    void allArgsConstructorCreatesCorrectObject() {
        CategoryResponseDto dto = new CategoryResponseDto(10, "Electronics");

        assertThat(dto.getId()).isEqualTo(10);
        assertThat(dto.getName()).isEqualTo("Electronics");
    }

    @Test
    void builderCreatesCorrectObject() {
        CategoryResponseDto dto = CategoryResponseDto.builder()
                .id(5)
                .name("Home")
                .build();

        assertThat(dto.getId()).isEqualTo(5);
        assertThat(dto.getName()).isEqualTo("Home");
    }

    // -------------------------------------------------------------------------
    // JSON Serialization
    // -------------------------------------------------------------------------

    @Test
    void serializesToJsonCorrectly() throws Exception {
        CategoryResponseDto dto = CategoryResponseDto.builder()
                .id(2)
                .name("Toys")
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"id\":2");
        assertThat(json).contains("\"name\":\"Toys\"");
    }

    @Test
    void deserializesFromJsonCorrectly() throws Exception {
        String json = """
                {
                  "id": 7,
                  "name": "Garden"
                }
                """;

        CategoryResponseDto dto =
                objectMapper.readValue(json, CategoryResponseDto.class);

        assertThat(dto.getId()).isEqualTo(7);
        assertThat(dto.getName()).isEqualTo("Garden");
    }
}

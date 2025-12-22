package at.technikum.springrestbackend.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ProductResponseDto}.
 */
class ProductResponseDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // -------------------------------------------------------------------------
    // Lombok
    // -------------------------------------------------------------------------

    @Test
    void getterAndSetterWorkCorrectly() {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(1);
        dto.setName("Product A");
        dto.setDescription("Description A");
        dto.setPrice(BigDecimal.valueOf(49.99));
        dto.setRating("5 stars");
        dto.setImagePath("/images/a.png");
        dto.setCategoryId(2);
        dto.setCategoryName("Category X");

        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getName()).isEqualTo("Product A");
        assertThat(dto.getDescription()).isEqualTo("Description A");
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(49.99));
        assertThat(dto.getRating()).isEqualTo("5 stars");
        assertThat(dto.getImagePath()).isEqualTo("/images/a.png");
        assertThat(dto.getCategoryId()).isEqualTo(2);
        assertThat(dto.getCategoryName()).isEqualTo("Category X");
    }

    @Test
    void allArgsConstructorCreatesCorrectObject() {
        ProductResponseDto dto = new ProductResponseDto(
                10,
                "Prod B",
                "Desc B",
                BigDecimal.valueOf(20.50),
                "4 stars",
                "/img/b.png",
                3,
                "Category Y"
        );

        assertThat(dto.getId()).isEqualTo(10);
        assertThat(dto.getName()).isEqualTo("Prod B");
        assertThat(dto.getDescription()).isEqualTo("Desc B");
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(20.50));
        assertThat(dto.getRating()).isEqualTo("4 stars");
        assertThat(dto.getImagePath()).isEqualTo("/img/b.png");
        assertThat(dto.getCategoryId()).isEqualTo(3);
        assertThat(dto.getCategoryName()).isEqualTo("Category Y");
    }

    @Test
    void builderCreatesCorrectObject() {
        ProductResponseDto dto = ProductResponseDto.builder()
                .id(5)
                .name("Builder Product")
                .description("Builder Desc")
                .price(BigDecimal.valueOf(99.99))
                .rating("5 stars")
                .imagePath("/img/builder.png")
                .categoryId(7)
                .categoryName("Category Z")
                .build();

        assertThat(dto.getId()).isEqualTo(5);
        assertThat(dto.getName()).isEqualTo("Builder Product");
        assertThat(dto.getDescription()).isEqualTo("Builder Desc");
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(99.99));
        assertThat(dto.getRating()).isEqualTo("5 stars");
        assertThat(dto.getImagePath()).isEqualTo("/img/builder.png");
        assertThat(dto.getCategoryId()).isEqualTo(7);
        assertThat(dto.getCategoryName()).isEqualTo("Category Z");
    }

    // -------------------------------------------------------------------------
    // JSON Serialization
    // -------------------------------------------------------------------------

    @Test
    void serializesToJsonCorrectly() throws Exception {
        ProductResponseDto dto = ProductResponseDto.builder()
                .id(20)
                .name("JSON Product")
                .description("JSON Desc")
                .price(BigDecimal.valueOf(49.99))
                .rating("5 stars")
                .imagePath("/images/json.png")
                .categoryId(3)
                .categoryName("Category JSON")
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"id\":20");
        assertThat(json).contains("\"name\":\"JSON Product\"");
        assertThat(json).contains("\"description\":\"JSON Desc\"");
        assertThat(json).contains("\"price\":49.99");
        assertThat(json).contains("\"rating\":\"5 stars\"");
        assertThat(json).contains("\"imagePath\":\"/images/json.png\"");
        assertThat(json).contains("\"categoryId\":3");
        assertThat(json).contains("\"categoryName\":\"Category JSON\"");
    }

    @Test
    void deserializesFromJsonCorrectly() throws Exception {
        String json = """
                {
                  "id": 15,
                  "name": "Prod JSON",
                  "description": "Desc JSON",
                  "price": 79.90,
                  "rating": "4 stars",
                  "imagePath": "/img/json.png",
                  "categoryId": 5,
                  "categoryName": "Cat JSON"
                }
                """;

        ProductResponseDto dto = objectMapper.readValue(json, ProductResponseDto.class);

        assertThat(dto.getId()).isEqualTo(15);
        assertThat(dto.getName()).isEqualTo("Prod JSON");
        assertThat(dto.getDescription()).isEqualTo("Desc JSON");
        assertThat(dto.getPrice()).isEqualTo(BigDecimal.valueOf(79.90));
        assertThat(dto.getRating()).isEqualTo("4 stars");
        assertThat(dto.getImagePath()).isEqualTo("/img/json.png");
        assertThat(dto.getCategoryId()).isEqualTo(5);
        assertThat(dto.getCategoryName()).isEqualTo("Cat JSON");
    }
}

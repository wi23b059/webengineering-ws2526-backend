package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.ProductRequestDto;
import at.technikum.springrestbackend.dto.ProductResponseDto;
import at.technikum.springrestbackend.entity.Category;
import at.technikum.springrestbackend.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    void toEntity_shouldMapProductRequestDtoToProductEntity() {
        // given
        ProductRequestDto dto = ProductRequestDto.builder()
                .name("Laptop")
                .description("High-end gaming laptop")
                .price(new BigDecimal("1499.99"))
                .rating("5 stars")
                .imagePath("/img/laptop.png")
                .categoryId(3)
                .build();

        // when
        Product product = ProductMapper.toEntity(dto);

        // then
        assertNotNull(product);
        assertEquals("Laptop", product.getName());
        assertEquals("High-end gaming laptop", product.getDescription());
        assertEquals(new BigDecimal("1499.99"), product.getPrice());
        assertEquals("5 stars", product.getRating());
        assertEquals("/img/laptop.png", product.getImagePath());

        // category is intentionally NOT set here (service responsibility)
        assertNull(product.getCategory());
    }

    @Test
    void toResponseDto_shouldMapProductEntityWithCategory() {
        // given
        Category category = Category.builder()
                .id(3)
                .name("Electronics")
                .build();

        Product product = Product.builder()
                .id(10)
                .name("Laptop")
                .description("High-end gaming laptop")
                .price(new BigDecimal("1499.99"))
                .rating("5 stars")
                .imagePath("/img/laptop.png")
                .category(category)
                .build();

        // when
        ProductResponseDto responseDto = ProductMapper.toResponseDto(product);

        // then
        assertNotNull(responseDto);
        assertEquals(10, responseDto.getId());
        assertEquals("Laptop", responseDto.getName());
        assertEquals("High-end gaming laptop", responseDto.getDescription());
        assertEquals(new BigDecimal("1499.99"), responseDto.getPrice());
        assertEquals("5 stars", responseDto.getRating());
        assertEquals("/img/laptop.png", responseDto.getImagePath());
        assertEquals(3, responseDto.getCategoryId());
        assertEquals("Electronics", responseDto.getCategoryName());
    }

    @Test
    void toResponseDto_shouldHandleNullCategoryGracefully() {
        // given
        Product product = Product.builder()
                .id(11)
                .name("Mouse")
                .description("Wireless mouse")
                .price(new BigDecimal("29.99"))
                .rating(null)
                .imagePath(null)
                .category(null)
                .build();

        // when
        ProductResponseDto responseDto = ProductMapper.toResponseDto(product);

        // then
        assertNotNull(responseDto);
        assertEquals(11, responseDto.getId());
        assertEquals("Mouse", responseDto.getName());
        assertEquals("Wireless mouse", responseDto.getDescription());
        assertEquals(new BigDecimal("29.99"), responseDto.getPrice());
        assertNull(responseDto.getCategoryId());
        assertNull(responseDto.getCategoryName());
    }
}

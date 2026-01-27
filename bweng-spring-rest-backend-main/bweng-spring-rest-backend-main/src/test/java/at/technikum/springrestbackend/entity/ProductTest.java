package at.technikum.springrestbackend.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Product}.
 */

class ProductTest {

    @Test
    void builder_shouldSetAllFieldsCorrectly() {
        // given
        Category category = Category.builder()
                .id(1)
                .name("Electronics")
                .build();

        // when
        Product product = Product.builder()
                .id(10)
                .name("Laptop")
                .description("Gaming Laptop")
                .price(BigDecimal.valueOf(1299.99))
                .rating("5 stars")
                .imagePath("/img/laptop.png")
                .category(category)
                .build();

        // then
        assertEquals(10, product.getId());
        assertEquals("Laptop", product.getName());
        assertEquals("Gaming Laptop", product.getDescription());
        assertEquals(BigDecimal.valueOf(1299.99), product.getPrice());
        assertEquals("5 stars", product.getRating());
        assertEquals("/img/laptop.png", product.getImagePath());
        assertEquals(category, product.getCategory());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // given
        Product product = new Product();
        Category category = new Category();

        // when
        product.setId(5);
        product.setName("Phone");
        product.setDescription("Smartphone");
        product.setPrice(BigDecimal.valueOf(699.99));
        product.setRating("4 stars");
        product.setImagePath("/img/phone.png");
        product.setCategory(category);

        // then
        assertEquals(5, product.getId());
        assertEquals("Phone", product.getName());
        assertEquals("Smartphone", product.getDescription());
        assertEquals(BigDecimal.valueOf(699.99), product.getPrice());
        assertEquals("4 stars", product.getRating());
        assertEquals("/img/phone.png", product.getImagePath());
        assertEquals(category, product.getCategory());
    }

    @Test
    void category_shouldBeNullable() {
        // when
        Product product = Product.builder()
                .name("Accessory")
                .price(BigDecimal.TEN)
                .build();

        // then
        assertNull(product.getCategory());
    }
}

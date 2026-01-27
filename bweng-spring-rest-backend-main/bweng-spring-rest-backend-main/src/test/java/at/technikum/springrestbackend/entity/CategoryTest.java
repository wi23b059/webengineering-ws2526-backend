package at.technikum.springrestbackend.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Category}.
 */

class CategoryTest {

    @Test
    void builder_shouldSetFieldsCorrectly() {
        // when
        Category category = Category.builder()
                .id(1)
                .name("Electronics")
                .build();

        // then
        assertEquals(1, category.getId());
        assertEquals("Electronics", category.getName());
        assertNull(category.getProducts(),
                "Products list should be null if not explicitly set");
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        // given
        Category category = new Category();

        // when
        category.setId(2);
        category.setName("Books");

        // then
        assertEquals(2, category.getId());
        assertEquals("Books", category.getName());
    }

    @Test
    void productsRelation_shouldAcceptProductList() {
        // given
        Category category = new Category();
        Product product1 = new Product();
        Product product2 = new Product();

        // when
        category.setProducts(List.of(product1, product2));

        // then
        assertNotNull(category.getProducts());
        assertEquals(2, category.getProducts().size());
        assertTrue(category.getProducts().contains(product1));
        assertTrue(category.getProducts().contains(product2));
    }
}

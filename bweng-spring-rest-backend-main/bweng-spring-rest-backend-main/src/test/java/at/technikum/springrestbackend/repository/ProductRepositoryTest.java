package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Category;
import at.technikum.springrestbackend.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ProductRepository}.
 */

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void saveAndFindByCategoryId_shouldReturnProductsForCategory() {
        // given
        Category category = Category.builder()
                .name("Electronics")
                .build();
        categoryRepository.save(category);

        Product product1 = Product.builder()
                .name("Laptop")
                .description("Gaming Laptop")
                .price(BigDecimal.valueOf(1200))
                .category(category)
                .build();

        Product product2 = Product.builder()
                .name("Headphones")
                .description("Wireless")
                .price(BigDecimal.valueOf(200))
                .category(category)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        // when
        List<Product> products = productRepository.findByCategoryId(category.getId());

        // then
        assertEquals(2, products.size());
        assertTrue(products.stream().allMatch(p -> p.getCategory().getId().equals(category.getId())));
    }

    @Test
    void findByCategoryId_shouldReturnEmptyListIfNoProducts() {
        List<Product> products = productRepository.findByCategoryId(9999);
        assertTrue(products.isEmpty());
    }
}

package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.ProductRequestDto;
import at.technikum.springrestbackend.entity.Category;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.exception.CategoryNotFoundException;
import at.technikum.springrestbackend.exception.ProductNotFoundException;
import at.technikum.springrestbackend.repository.CategoryRepository;
import at.technikum.springrestbackend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_shouldReturnAllProducts_whenCategoryIdIsNull() {
        Product product = new Product();
        product.setId(1);

        when(productRepository.findAll())
                .thenReturn(List.of(product));

        var result = productService.getAllProducts(null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
    }

    @Test
    void getAllProducts_shouldReturnProductsByCategory() {
        Product product = new Product();
        product.setId(1);

        when(productRepository.findByCategoryId(2))
                .thenReturn(List.of(product));

        var result = productService.getAllProducts(2);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
    }

    @Test
    void getProduct_shouldReturnProduct() {
        Product product = new Product();
        product.setId(1);

        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        var result = productService.getProduct(1);

        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void getProduct_shouldThrowException_whenNotFound() {
        when(productRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProduct(1))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void createProduct_shouldSaveProduct_withoutCategory() {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Test");
        dto.setPrice(BigDecimal.valueOf(9.99));

        Product saved = new Product();
        saved.setId(1);

        when(productRepository.save(any(Product.class)))
                .thenReturn(saved);

        var result = productService.createProduct(dto);

        assertThat(result.getId()).isEqualTo(1);
        verify(productRepository).save(any(Product.class));
        verifyNoInteractions(categoryRepository);
    }

    @Test
    void createProduct_shouldSaveProduct_withCategory() {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Test");
        dto.setCategoryId(5);

        Category category = new Category();
        category.setId(5);

        Product saved = new Product();
        saved.setId(1);

        when(categoryRepository.findById(5))
                .thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class)))
                .thenReturn(saved);

        var result = productService.createProduct(dto);

        assertThat(result.getId()).isEqualTo(1);
        verify(categoryRepository).findById(5);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_shouldThrowException_whenCategoryNotFound() {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setCategoryId(99);

        when(categoryRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.createProduct(dto))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    void updateProduct_shouldUpdateProductAndCategory() {
        Product existing = new Product();
        existing.setId(1);

        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Updated");
        dto.setCategoryId(3);

        Category category = new Category();
        category.setId(3);

        when(productRepository.findById(1))
                .thenReturn(Optional.of(existing));
        when(categoryRepository.findById(3))
                .thenReturn(Optional.of(category));
        when(productRepository.save(existing))
                .thenReturn(existing);

        var result = productService.updateProduct(1, dto);

        assertThat(result.getId()).isEqualTo(1);
        verify(productRepository).save(existing);
    }

    @Test
    void updateProduct_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(1))
                .thenReturn(Optional.empty());

        ProductRequestDto dto = new ProductRequestDto();

        assertThatThrownBy(() -> productService.updateProduct(1, dto))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void updateProduct_shouldThrowException_whenCategoryNotFound() {
        Product existing = new Product();
        existing.setId(1);

        ProductRequestDto dto = new ProductRequestDto();
        dto.setCategoryId(10);

        when(productRepository.findById(1))
                .thenReturn(Optional.of(existing));
        when(categoryRepository.findById(10))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(1, dto))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    void deleteProduct_shouldDeleteProduct() {
        when(productRepository.existsById(1))
                .thenReturn(true);

        productService.deleteProduct(1);

        verify(productRepository).deleteById(1);
    }

    @Test
    void deleteProduct_shouldThrowException_whenNotFound() {
        when(productRepository.existsById(1))
                .thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(1))
                .isInstanceOf(ProductNotFoundException.class);
    }
}

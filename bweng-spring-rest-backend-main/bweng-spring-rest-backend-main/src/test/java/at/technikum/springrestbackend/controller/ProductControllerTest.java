package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.ProductRequestDto;
import at.technikum.springrestbackend.dto.ProductResponseDto;
import at.technikum.springrestbackend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductResponseDto sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleProduct = ProductResponseDto.builder()
                .id(1)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(99.99))
                .categoryId(1)
                .build();
    }

    @Test
    void testGetAllProducts_NoCategory() {
        when(productService.getAllProducts(null)).thenReturn(List.of(sampleProduct));

        List<ProductResponseDto> result = productController.getAllProducts(null);

        assertEquals(1, result.size());
        assertEquals(sampleProduct.getId(), result.getFirst().getId());
        verify(productService).getAllProducts(null);
    }

    @Test
    void testGetAllProducts_WithCategory() {
        when(productService.getAllProducts(1)).thenReturn(List.of(sampleProduct));

        List<ProductResponseDto> result = productController.getAllProducts(1);

        assertEquals(1, result.size());
        assertEquals(sampleProduct.getId(), result.getFirst().getId());
        verify(productService).getAllProducts(1);
    }

    @Test
    void testGetProductById() {
        when(productService.getProduct(1)).thenReturn(sampleProduct);

        ProductResponseDto result = productController.getProductById(1);

        assertEquals(sampleProduct.getId(), result.getId());
        verify(productService).getProduct(1);
    }

    @Test
    void testCreateProduct() {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(99.99))
                .categoryId(1)
                .build();

        when(productService.createProduct(requestDto)).thenReturn(sampleProduct);

        ResponseEntity<ProductResponseDto> response = productController.createProduct(requestDto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(sampleProduct.getId(), response.getBody().getId());
        verify(productService).createProduct(requestDto);
    }

    @Test
    void testUpdateProduct() {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .name("Updated Product")
                .description("Updated Description")
                .price(BigDecimal.valueOf(109.99))
                .categoryId(1)
                .build();

        ProductResponseDto updatedProduct = ProductResponseDto.builder()
                .id(1)
                .name("Updated Product")
                .description("Updated Description")
                .price(BigDecimal.valueOf(109.99))
                .categoryId(1)
                .build();

        when(productService.updateProduct(1, requestDto)).thenReturn(updatedProduct);

        ResponseEntity<ProductResponseDto> response = productController.updateProduct(1, requestDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(updatedProduct.getName(), response.getBody().getName());
        verify(productService).updateProduct(1, requestDto);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1);

        ResponseEntity<Void> response = productController.deleteProduct(1);

        assertEquals(204, response.getStatusCode().value());
        verify(productService).deleteProduct(1);
    }
}

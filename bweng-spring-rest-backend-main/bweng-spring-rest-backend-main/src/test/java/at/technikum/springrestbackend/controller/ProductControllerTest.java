package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.ProductRequestDto;
import at.technikum.springrestbackend.dto.ProductResponseDto;
import at.technikum.springrestbackend.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests for {@link ProductController}.
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    // -------------------------------------------------------------------------
    // GET all products
    // -------------------------------------------------------------------------

    @Test
    void getAllProductsWithoutCategoryReturnsList() throws Exception {
        when(productService.getAllProducts(null))
                .thenReturn(List.of(new ProductResponseDto()));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getAllProductsWithCategoryReturnsFilteredList() throws Exception {
        when(productService.getAllProducts(5))
                .thenReturn(List.of(new ProductResponseDto()));

        mockMvc.perform(get("/api/products")
                        .param("category", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // -------------------------------------------------------------------------
    // GET product by id
    // -------------------------------------------------------------------------

    @Test
    void getProductByIdReturnsProduct() throws Exception {
        when(productService.getProduct(1))
                .thenReturn(new ProductResponseDto());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // POST create product
    // -------------------------------------------------------------------------

    @Test
    void createProductReturnsCreated() throws Exception {
        ProductRequestDto requestDto = new ProductRequestDto();
        // Felder optional setzen, je nach @NotNull/@NotBlank im DTO

        when(productService.createProduct(any(ProductRequestDto.class)))
                .thenReturn(new ProductResponseDto());

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void createProductReturnsBadRequestWhenInvalid() throws Exception {
        ProductRequestDto invalidDto = new ProductRequestDto();

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // -------------------------------------------------------------------------
    // PUT update product
    // -------------------------------------------------------------------------

    @Test
    void updateProductReturnsUpdatedProduct() throws Exception {
        ProductRequestDto requestDto = new ProductRequestDto();

        when(productService.updateProduct(
                eq(1),
                any(ProductRequestDto.class)))
                .thenReturn(new ProductResponseDto());

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // DELETE product
    // -------------------------------------------------------------------------

    @Test
    void deleteProductReturnsNoContent() throws Exception {
        doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}

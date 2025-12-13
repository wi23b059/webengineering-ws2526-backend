package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.ProductRequestDto;
import at.technikum.springrestbackend.dto.ProductResponseDto;
import at.technikum.springrestbackend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints for products.
 * Supports optional filtering by category via a query parameter.
 */
//@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    /**
     * Returns all products, optionally filtered by category.
     *
     * @param category optional category id used to filter products
     * @return a list of product response DTOs
     */
    @GetMapping
    public List<ProductResponseDto> getAllProducts(@RequestParam(required = false) Integer category) {
        return productService.getAllProducts(category);
    }

    /**
     * Returns a single product identified by its id.
     *
     * @param id the id of the product to retrieve
     * @return the product as a response DTO
     */
    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Integer id) {
        return productService.getProduct(id);
    }

    /**
     * Creates a new product using the provided request data.
     *
     * @param dto the product creation request
     * @return a 201 Created response containing the created product
     */
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto dto) {
        ProductResponseDto saved = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Updates an existing product identified by its id.
     *
     * @param id  the id of the product to update
     * @param dto the updated values for the product
     * @return a 200 OK response containing the updated product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequestDto dto) {
        ProductResponseDto updated = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes the product with the given id.
     *
     * @param id the id of the product to delete
     * @return a 204 No Content response if the deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

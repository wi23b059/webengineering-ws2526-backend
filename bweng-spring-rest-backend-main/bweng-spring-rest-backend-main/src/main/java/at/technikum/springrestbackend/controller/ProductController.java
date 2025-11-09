package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.ProductRequestDto;
import at.technikum.springrestbackend.dto.ProductResponseDto;
import at.technikum.springrestbackend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    // GET /api/products
    @GetMapping
    public List<ProductResponseDto> getAllProducts(@RequestParam(required = false) Integer category) {
        return productService.getAllProducts(category);
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Integer id) {
        return productService.getProduct(id);
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto dto) {
        ProductResponseDto saved = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequestDto dto) {
        ProductResponseDto updated = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

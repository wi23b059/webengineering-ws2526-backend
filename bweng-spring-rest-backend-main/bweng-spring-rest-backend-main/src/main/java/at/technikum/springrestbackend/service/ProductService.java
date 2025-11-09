package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.ProductRequestDto;
import at.technikum.springrestbackend.dto.ProductResponseDto;
import at.technikum.springrestbackend.entity.Category;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.mapper.ProductMapper;
import at.technikum.springrestbackend.repository.CategoryRepository;
import at.technikum.springrestbackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductResponseDto> getAllProducts(Integer categoryId) {
        List<Product> products;
        if (categoryId == null) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findByCategoryId(categoryId);
        }
        return products.stream().map(ProductMapper::toResponseDto).toList();
    }

    public ProductResponseDto getProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.toResponseDto(product);
    }

    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = ProductMapper.toEntity(dto);
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        Product saved = productRepository.save(product);
        return ProductMapper.toResponseDto(saved);
    }

    public ProductResponseDto updateProduct(Integer id, ProductRequestDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setRating(dto.getRating());
        existing.setImagePath(dto.getImagePath());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existing.setCategory(category);
        } else {
            existing.setCategory(null);
        }

        Product updated = productRepository.save(existing);
        return ProductMapper.toResponseDto(updated);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}

package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.ProductRequestDto;
import at.technikum.springrestbackend.dto.ProductResponseDto;
import at.technikum.springrestbackend.entity.Category;
import at.technikum.springrestbackend.entity.Product;
import at.technikum.springrestbackend.exception.CategoryNotFoundException;
import at.technikum.springrestbackend.exception.ProductNotFoundException;
import at.technikum.springrestbackend.mapper.ProductMapper;
import at.technikum.springrestbackend.repository.CategoryRepository;
import at.technikum.springrestbackend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Returns a list of all products in the system.
     *
     * @return a list of product response DTOs
     */
    public List<ProductResponseDto> getAllProducts(Integer categoryId) {
        List<Product> products;
        if (categoryId == null) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findByCategoryId(categoryId);
        }
        return products.stream().map(ProductMapper::toResponseDto).toList();
    }

    /**
     * Returns a single product by its id.
     *
     * @param id the product id
     * @return the product as a response DTO
     * @throws ProductNotFoundException if no product with the given id exists
     */
    public ProductResponseDto getProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toResponseDto(product);
    }

    /**
     * Creates a new product based on the given request data.
     * The category is resolved from the provided category id, if present.
     *
     * @param dto the product creation request
     * @return the created product as a response DTO
     * @throws CategoryNotFoundException if the provided category id does not exist
     */
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = ProductMapper.toEntity(dto);
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));
            product.setCategory(category);
        }
        Product saved = productRepository.save(product);
        return ProductMapper.toResponseDto(saved);
    }

    /**
     * Updates the product with the given id using the provided request data.
     *
     * @param id  the id of the product to update
     * @param dto the updated values for the product
     * @return the updated product as a response DTO
     * @throws ProductNotFoundException  if no product with the given id exists
     * @throws CategoryNotFoundException if the provided category id does not exist
     */
    @Transactional
    public ProductResponseDto updateProduct(Integer id, ProductRequestDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setRating(dto.getRating());
        existing.setImagePath(dto.getImagePath());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(id));
            existing.setCategory(category);
        } else {
            existing.setCategory(null);
        }

        Product updated = productRepository.save(existing);
        return ProductMapper.toResponseDto(updated);
    }

    /**
     * Deletes the product with the given id.
     *
     * @param id the id of the product to delete
     * @throws ProductNotFoundException if no product with the given id exists
     */
    @Transactional
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}

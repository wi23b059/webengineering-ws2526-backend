package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.ProductRequestDto;
import at.technikum.springrestbackend.dto.ProductResponseDto;
import at.technikum.springrestbackend.entity.Product;

/**
 * Mapper for converting between {@link Product} entities and their request/response DTOs.
 */
public class ProductMapper {

    /**
     * Creates a new {@link Product} entity from the given request DTO.
     * <p>
     * The {@code category} reference must be set in the service layer.
     */
    public static Product toEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .rating(dto.getRating())
                .imagePath(dto.getImagePath())
                .build();
    }


    /**
     * Creates a response DTO from the given {@link Product} entity.
     */
    public static ProductResponseDto toResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .rating(product.getRating())
                .imagePath(product.getImagePath())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }
}

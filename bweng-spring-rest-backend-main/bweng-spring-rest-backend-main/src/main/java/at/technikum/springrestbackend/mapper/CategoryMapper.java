package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.CategoryRequestDto;
import at.technikum.springrestbackend.dto.CategoryResponseDto;
import at.technikum.springrestbackend.entity.Category;

/**
 * Mapper for converting between {@link Category} entities and their request/response DTOs.
 */
public class CategoryMapper {

    /**
     * Creates a new {@link Category} entity from the given request DTO.
     */
    public static Category toEntity(CategoryRequestDto dto) {
        // Kept the builder for visual consistency, but still looks like overkill
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    /**
     * Creates a response DTO from the given {@link Category} entity.
     */
    public static CategoryResponseDto toResponseDto(Category category) {
        // Same as above with the builder
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}

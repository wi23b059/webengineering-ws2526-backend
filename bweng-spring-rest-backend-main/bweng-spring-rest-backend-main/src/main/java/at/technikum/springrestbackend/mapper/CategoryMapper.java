package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.CategoryRequestDto;
import at.technikum.springrestbackend.dto.CategoryResponseDto;
import at.technikum.springrestbackend.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public static CategoryResponseDto toResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}

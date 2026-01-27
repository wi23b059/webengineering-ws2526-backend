package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.CategoryRequestDto;
import at.technikum.springrestbackend.dto.CategoryResponseDto;
import at.technikum.springrestbackend.entity.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CategoryMapper}.
 */

class CategoryMapperTest {

    @Test
    void toEntity_shouldMapRequestDtoToCategoryEntity() {
        // given
        CategoryRequestDto dto = new CategoryRequestDto("Electronics");

        // when
        Category category = CategoryMapper.toEntity(dto);

        // then
        assertNotNull(category);
        assertEquals("Electronics", category.getName());
    }

    @Test
    void toResponseDto_shouldMapCategoryEntityToResponseDto() {
        // given
        Category category = Category.builder()
                .id(10)
                .name("Books")
                .build();

        // when
        CategoryResponseDto responseDto = CategoryMapper.toResponseDto(category);

        // then
        assertNotNull(responseDto);
        assertEquals(10, responseDto.getId());
        assertEquals("Books", responseDto.getName());
    }
}

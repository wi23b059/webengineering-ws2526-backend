package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.CategoryRequestDto;
import at.technikum.springrestbackend.dto.CategoryResponseDto;
import at.technikum.springrestbackend.entity.Category;
import at.technikum.springrestbackend.exception.CategoryNotFoundException;
import at.technikum.springrestbackend.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    private Category createCategory(Integer id, String name) {
        return Category.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Test
    void getAllCategories_returnsMappedList() {
        when(categoryRepository.findAll())
                .thenReturn(List.of(
                        createCategory(1, "Food"),
                        createCategory(2, "Drinks")
                ));

        List<CategoryResponseDto> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("Food", result.get(0).getName());
        assertEquals("Drinks", result.get(1).getName());
    }

    @Test
    void getCategory_returnsCategoryIfFound() {
        Category category = createCategory(1, "Food");

        when(categoryRepository.findById(1))
                .thenReturn(Optional.of(category));

        CategoryResponseDto result = categoryService.getCategory(1);

        assertEquals(1, result.getId());
        assertEquals("Food", result.getName());
    }

    @Test
    void getCategory_throwsExceptionIfNotFound() {
        when(categoryRepository.findById(99))
                .thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.getCategory(99)
        );
    }

    @Test
    void createCategory_savesAndReturnsResponse() {
        CategoryRequestDto dto = new CategoryRequestDto();
        dto.setName("Electronics");

        Category saved = createCategory(1, "Electronics");

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(saved);

        CategoryResponseDto result = categoryService.createCategory(dto);

        assertEquals("Electronics", result.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void updateCategory_updatesExistingCategory() {
        Category existing = createCategory(1, "Old");

        CategoryRequestDto dto = new CategoryRequestDto();
        dto.setName("New");

        when(categoryRepository.findById(1))
                .thenReturn(Optional.of(existing));
        when(categoryRepository.save(existing))
                .thenReturn(existing);

        CategoryResponseDto result = categoryService.updateCategory(1, dto);

        assertEquals("New", result.getName());
        verify(categoryRepository).save(existing);
    }

    @Test
    void updateCategory_throwsExceptionIfNotFound() {
        when(categoryRepository.findById(1))
                .thenReturn(Optional.empty());

        CategoryRequestDto dto = new CategoryRequestDto();
        dto.setName("DoesNotMatter");

        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.updateCategory(1, dto)
        );
    }

    @Test
    void deleteCategory_deletesIfExists() {
        when(categoryRepository.existsById(1))
                .thenReturn(true);

        categoryService.deleteCategory(1);

        verify(categoryRepository).deleteById(1);
    }

    @Test
    void deleteCategory_throwsExceptionIfNotExists() {
        when(categoryRepository.existsById(1))
                .thenReturn(false);

        assertThrows(
                CategoryNotFoundException.class,
                () -> categoryService.deleteCategory(1)
        );
    }
}

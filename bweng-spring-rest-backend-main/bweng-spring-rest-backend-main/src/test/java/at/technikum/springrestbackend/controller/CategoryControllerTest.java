package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.CategoryRequestDto;
import at.technikum.springrestbackend.dto.CategoryResponseDto;
import at.technikum.springrestbackend.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private CategoryResponseDto sampleCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleCategory = new CategoryResponseDto();
        sampleCategory.setId(1);
        sampleCategory.setName("Sample Category");
    }

    @Test
    void testGetAllCategories() {
        when(categoryService.getAllCategories()).thenReturn(List.of(sampleCategory));

        List<CategoryResponseDto> result = categoryController.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Sample Category", result.getFirst().getName());
        verify(categoryService).getAllCategories();
    }

    @Test
    void testGetCategory() {
        when(categoryService.getCategory(1)).thenReturn(sampleCategory);

        CategoryResponseDto result = categoryController.getCategory(1);

        assertEquals("Sample Category", result.getName());
        verify(categoryService).getCategory(1);
    }

    @Test
    void testCreateCategory() {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Sample Category");

        when(categoryService.createCategory(requestDto)).thenReturn(sampleCategory);

        ResponseEntity<CategoryResponseDto> response = categoryController.createCategory(requestDto);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("Sample Category", response.getBody().getName());
        verify(categoryService).createCategory(requestDto);
    }

    @Test
    void testUpdateCategory() {
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Updated Category");

        CategoryResponseDto updatedCategory = new CategoryResponseDto();
        updatedCategory.setId(1);
        updatedCategory.setName("Updated Category");

        when(categoryService.updateCategory(1, requestDto)).thenReturn(updatedCategory);

        ResponseEntity<CategoryResponseDto> response = categoryController.updateCategory(1, requestDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Updated Category", response.getBody().getName());
        verify(categoryService).updateCategory(1, requestDto);
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryService).deleteCategory(1);

        ResponseEntity<Void> response = categoryController.deleteCategory(1);

        assertEquals(204, response.getStatusCode().value());
        verify(categoryService).deleteCategory(1);
    }
}

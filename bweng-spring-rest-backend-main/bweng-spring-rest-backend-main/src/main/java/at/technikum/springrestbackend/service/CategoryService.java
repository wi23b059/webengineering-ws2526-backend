package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.CategoryRequestDto;
import at.technikum.springrestbackend.dto.CategoryResponseDto;
import at.technikum.springrestbackend.entity.Category;
import at.technikum.springrestbackend.mapper.CategoryMapper;
import at.technikum.springrestbackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponseDto)
                .toList();
    }

    public CategoryResponseDto getCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return CategoryMapper.toResponseDto(category);
    }

    public CategoryResponseDto createCategory(CategoryRequestDto dto) {
        Category category = CategoryMapper.toEntity(dto);
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toResponseDto(saved);
    }

    public CategoryResponseDto updateCategory(Integer id, CategoryRequestDto dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(dto.getName());

        Category updated = categoryRepository.save(existing);
        return CategoryMapper.toResponseDto(updated);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}

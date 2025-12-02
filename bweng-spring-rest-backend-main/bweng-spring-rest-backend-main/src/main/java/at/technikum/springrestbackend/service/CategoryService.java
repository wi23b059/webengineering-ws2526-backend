package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.CategoryRequestDto;
import at.technikum.springrestbackend.dto.CategoryResponseDto;
import at.technikum.springrestbackend.entity.Category;
import at.technikum.springrestbackend.exception.CategoryNotFoundException;
import at.technikum.springrestbackend.mapper.CategoryMapper;
import at.technikum.springrestbackend.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service providing CRUD operations for {@code Category} entities.
 * Uses {@link CategoryRepository} and {@link CategoryMapper} to access and expose data.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Returns a list of all categories in the system.
     */
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponseDto)
                .toList();
    }

    /**
     * Returns a single category by its id.
     *
     * @param id the category id
     * @throws CategoryNotFoundException if no category with the given id exists
     */
    public CategoryResponseDto getCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return CategoryMapper.toResponseDto(category);
    }

    /**
     * Creates a new category based on the given request data.
     *
     * @param dto the category creation request
     * @return the created category as a response DTO
     */
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {
        Category category = CategoryMapper.toEntity(dto);
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toResponseDto(saved);
    }

    /**
     * Updates the category with the given id using the provided request data.
     *
     * @param id  the id of the category to update
     * @param dto the updated values for the category
     * @return the updated category as a response DTO
     * @throws CategoryNotFoundException if no category with the given id exists
     */
    @Transactional
    public CategoryResponseDto updateCategory(Integer id, CategoryRequestDto dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        existing.setName(dto.getName());

        Category updated = categoryRepository.save(existing);
        return CategoryMapper.toResponseDto(updated);
    }

    /**
     * Deletes the category with the given id.
     *
     * @param id the id of the category to delete
     * @throws CategoryNotFoundException if no category with the given id exists
     */
    @Transactional
    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
    }
}

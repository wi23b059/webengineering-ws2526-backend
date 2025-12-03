package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.CategoryRequestDto;
import at.technikum.springrestbackend.dto.CategoryResponseDto;
import at.technikum.springrestbackend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints for categories.
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Returns all categories.
     *
     * @return a list of category response DTOs
     */
    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Returns a single category identified by its id.
     *
     * @param id the id of the category to retrieve
     * @return the category as a response DTO
     */
    @GetMapping("/{id}")
    public CategoryResponseDto getCategory(@PathVariable Integer id) {
        return categoryService.getCategory(id);
    }

    /**
     * Creates a new category using the provided request data.
     *
     * @param dto the category data sent by the client
     * @return a 201 Created response containing the created category
     */
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto dto) {
        CategoryResponseDto saved = categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Updates an existing category identified by its id.
     *
     * @param id  the id of the category to update
     * @param dto the updated values for the category
     * @return a 200 OK response containing the updated category
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryRequestDto dto) {
        CategoryResponseDto updated = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes the category with the given id.
     *
     * @param id the id of the category to delete
     * @return a 204 No Content response if the deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

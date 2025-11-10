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

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    // GET /api/categories
    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // GET /api/categories/{id}
    @GetMapping("/{id}")
    public CategoryResponseDto getCategory(@PathVariable Integer id) {
        return categoryService.getCategory(id);
    }

    // POST /api/categories
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto dto) {
        CategoryResponseDto saved = categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/categories/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryRequestDto dto) {
        CategoryResponseDto updated = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/categories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

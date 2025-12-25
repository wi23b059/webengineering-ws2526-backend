package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void saveAndFindById_shouldPersistCategory() {
        // given
        Category category = Category.builder()
                .name("Electronics")
                .build();

        Category saved = categoryRepository.save(category);

        // when
        Optional<Category> result = categoryRepository.findById(saved.getId());

        // then
        assertTrue(result.isPresent());
        assertEquals("Electronics", result.get().getName());
    }

    @Test
    void delete_shouldRemoveCategory() {
        // given
        Category category = Category.builder()
                .name("Books")
                .build();

        Category saved = categoryRepository.save(category);

        // when
        categoryRepository.deleteById(saved.getId());

        // then
        Optional<Category> result = categoryRepository.findById(saved.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllCategories() {
        // given
        categoryRepository.save(
                Category.builder().name("Sports").build()
        );
        categoryRepository.save(
                Category.builder().name("Clothing").build()
        );

        // when
        var categories = categoryRepository.findAll();

        // then
        assertEquals(2, categories.size());
    }
}

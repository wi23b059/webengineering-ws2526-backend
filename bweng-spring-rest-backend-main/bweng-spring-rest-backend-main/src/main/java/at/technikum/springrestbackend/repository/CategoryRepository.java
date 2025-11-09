package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

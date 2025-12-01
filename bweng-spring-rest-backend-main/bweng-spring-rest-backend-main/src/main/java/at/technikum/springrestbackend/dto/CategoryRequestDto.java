package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Request DTO used to create or update a category.
 * Contains the client-provided data for the {@code Category} entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 90, message = "Name must be at most 90 characters")
    private String name;
}

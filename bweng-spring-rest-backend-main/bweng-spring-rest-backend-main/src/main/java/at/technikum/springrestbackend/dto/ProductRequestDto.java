package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

/**
 * Request DTO used to create or update a product.
 * Contains client-provided data for the {@code Product} entity, including the target category.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    @NotBlank(message = "Name is required")
    @Size(max = 90, message = "First name must be at most 90 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @Size(max = 120, message = "Rating must be at most 120 characters")
    private String rating;

    @Size(max = 120, message = "Image path must be at most 120 characters")
    private String imagePath;

    @NotNull(message = "Category id is required")
    private Integer categoryId;
}

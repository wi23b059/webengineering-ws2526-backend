package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

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

    @NotNull
    @Positive
    private BigDecimal price;

    private String rating;

    private String imagePath;

    private Integer categoryId;
}

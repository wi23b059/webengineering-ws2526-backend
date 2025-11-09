package at.technikum.springrestbackend.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Integer id;

    private String name;
    private String description;
    private BigDecimal price;

    private String rating;

    private String imagePath;

    private Integer categoryId;
    private String categoryName;
}

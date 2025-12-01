package at.technikum.springrestbackend.dto;

import lombok.*;

/**
 * Response DTO representing a category returned by the API.
 * Exposes the category identifier and name.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {
    private Integer id;
    private String name;
}

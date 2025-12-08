package at.technikum.springrestbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDto {

    private Integer id;
    private String userId;
    private Integer productId;
    private Integer quantity;
}

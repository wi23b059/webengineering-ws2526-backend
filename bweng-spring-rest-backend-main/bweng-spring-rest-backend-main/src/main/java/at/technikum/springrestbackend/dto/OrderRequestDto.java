package at.technikum.springrestbackend.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    @NotNull
    private String userId;

    @NotNull
    private BigDecimal totalPrice;

    private String paymentMethod;
}

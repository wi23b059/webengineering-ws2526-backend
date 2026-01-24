package at.technikum.springrestbackend.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private Integer id;
    private String userId;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private String paymentMethod;
    private String invoiceNumber;
    private List<OrderItemResponseDto> items;
}

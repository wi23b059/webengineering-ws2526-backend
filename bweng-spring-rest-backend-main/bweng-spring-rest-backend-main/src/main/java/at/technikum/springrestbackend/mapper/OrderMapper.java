package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.OrderRequestDto;
import at.technikum.springrestbackend.dto.OrderResponseDto;
import at.technikum.springrestbackend.entity.Order;

public class OrderMapper {

    public static Order toEntity(OrderRequestDto dto) {
        return Order.builder()
                .userId(dto.getUserId())
                .totalPrice(dto.getTotalPrice())
                .paymentMethod(dto.getPaymentMethod())
                .status(Order.Status.pending)
                .build();
    }

    public static OrderResponseDto toResponseDto(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .paymentMethod(order.getPaymentMethod())
                .invoiceNumber(order.getInvoiceNumber())
                .build();
    }
}

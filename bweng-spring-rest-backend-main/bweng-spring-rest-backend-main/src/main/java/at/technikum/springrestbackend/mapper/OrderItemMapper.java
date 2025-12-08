package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.dto.OrderItemResponseDto;
import at.technikum.springrestbackend.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItem toEntity(Integer orderId, OrderItemRequestDto dto) {
        return OrderItem.builder()
                .orderId(orderId)
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .build();
    }

    public static OrderItemResponseDto toResponseDto(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrderId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .createdAt(orderItem.getCreatedAt())
                .build();
    }
}

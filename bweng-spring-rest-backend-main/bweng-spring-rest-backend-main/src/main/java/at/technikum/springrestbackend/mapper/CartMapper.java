package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.CartItemRequestDto;
import at.technikum.springrestbackend.dto.CartItemResponseDto;
import at.technikum.springrestbackend.entity.Cart;

public class CartMapper {

    public static Cart toEntity(String userId, CartItemRequestDto dto) {
        return Cart.builder()
                .userId(userId)
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .build();
    }

    public static CartItemResponseDto toResponseDto(Cart cart) {
        return CartItemResponseDto.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .productId(cart.getProductId())
                .quantity(cart.getQuantity())
                .build();
    }
}

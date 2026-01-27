package at.technikum.springrestbackend.mapper;

import at.technikum.springrestbackend.dto.CartItemRequestDto;
import at.technikum.springrestbackend.dto.CartItemResponseDto;
import at.technikum.springrestbackend.entity.Cart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CartMapper}.
 */

class CartMapperTest {

    @Test
    void toEntity_shouldMapRequestDtoToCartEntity() {
        // given
        String userId = "user-123";
        CartItemRequestDto dto = CartItemRequestDto.builder()
                .productId(42)
                .quantity(3)
                .build();

        // when
        Cart cart = CartMapper.toEntity(userId, dto);

        // then
        assertNotNull(cart);
        assertEquals(userId, cart.getUserId());
        assertEquals(42, cart.getProductId());
        assertEquals(3, cart.getQuantity());
    }

    @Test
    void toResponseDto_shouldMapCartEntityToResponseDto() {
        // given
        Cart cart = Cart.builder()
                .id(1)
                .userId("user-123")
                .productId(99)
                .quantity(5)
                .build();

        // when
        CartItemResponseDto responseDto = CartMapper.toResponseDto(cart);

        // then
        assertNotNull(responseDto);
        assertEquals(1, responseDto.getId());
        assertEquals("user-123", responseDto.getUserId());
        assertEquals(99, responseDto.getProductId());
        assertEquals(5, responseDto.getQuantity());
    }
}

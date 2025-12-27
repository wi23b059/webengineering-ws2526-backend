package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.CartItemRequestDto;
import at.technikum.springrestbackend.dto.CartItemResponseDto;
import at.technikum.springrestbackend.entity.Cart;
import at.technikum.springrestbackend.exception.CartNotFoundException;
import at.technikum.springrestbackend.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    CartService cartService;

    private Cart createCart(String userId, Integer productId, int quantity) {
        return Cart.builder()
                .id(1)
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .build();
    }

    @Test
    void getCart_returnsMappedCartItems() {
        String userId = "user-1";
        Cart cart = createCart(userId, 10, 2);

        when(cartRepository.findByUserId(userId))
                .thenReturn(List.of(cart));

        List<CartItemResponseDto> result = cartService.getCart(userId);

        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getProductId());
        assertEquals(2, result.get(0).getQuantity());
    }

    @Test
    void addCartItem_savesAndReturnsResponseDto() {
        String userId = "user-1";

        CartItemRequestDto dto = new CartItemRequestDto();
        dto.setProductId(5);
        dto.setQuantity(3);

        Cart savedCart = createCart(userId, 5, 3);

        when(cartRepository.save(any(Cart.class)))
                .thenReturn(savedCart);

        CartItemResponseDto response = cartService.addCartItem(userId, dto);

        assertEquals(5, response.getProductId());
        assertEquals(3, response.getQuantity());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void updateCartItem_updatesQuantitySuccessfully() {
        String userId = "user-1";
        Integer productId = 7;

        Cart existingCart = createCart(userId, productId, 1);

        CartItemRequestDto dto = new CartItemRequestDto();
        dto.setQuantity(4);

        when(cartRepository.findByUserIdAndProductId(userId, productId))
                .thenReturn(Optional.of(existingCart));
        when(cartRepository.save(existingCart))
                .thenReturn(existingCart);

        CartItemResponseDto response =
                cartService.updateCartItem(userId, productId, dto);

        assertEquals(4, response.getQuantity());
        verify(cartRepository).save(existingCart);
    }

    @Test
    void updateCartItem_throwsExceptionIfNotFound() {
        when(cartRepository.findByUserIdAndProductId("user-x", 99))
                .thenReturn(Optional.empty());

        CartItemRequestDto dto = new CartItemRequestDto();
        dto.setQuantity(1);

        assertThrows(
                CartNotFoundException.class,
                () -> cartService.updateCartItem("user-x", 99, dto)
        );
    }

    @Test
    void deleteCartItem_callsRepositoryDelete() {
        cartService.deleteCartItem("user-1", 3);

        verify(cartRepository)
                .deleteByUserIdAndProductId("user-1", 3);
    }
}

package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.CartItemRequestDto;
import at.technikum.springrestbackend.dto.CartItemResponseDto;
import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.entity.Role;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CartController}.
 */

class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Mock
    private Authentication authentication;

    private final String userId = "12345";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCart_ShouldReturnCartItems() {
        // Arrange
        CartItemResponseDto item = new CartItemResponseDto(1, "Product1", 2, 10);
        when(cartService.getCart(userId)).thenReturn(List.of(item));

        // Act
        List<CartItemResponseDto> result = cartController.getCart(userId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(item.getProductId(), result.getFirst().getProductId());
        verify(cartService, times(1)).getCart(userId);
    }

    @Test
    void addCartItem_ShouldReturnCreatedItem() {
        CartItemRequestDto requestDto = new CartItemRequestDto(1, 2);
        CartItemResponseDto responseDto = new CartItemResponseDto(1, "Product1", 2, 10);

        when(cartService.addCartItem(userId, requestDto)).thenReturn(responseDto);

        CartItemResponseDto result = cartController.addCartItem(userId, requestDto);

        assertEquals(responseDto.getProductId(), result.getProductId());
        verify(cartService, times(1)).addCartItem(userId, requestDto);
    }

    @Test
    void updateCartItem_ShouldReturnUpdatedItem() {
        Integer productId = 1;
        CartItemRequestDto requestDto = new CartItemRequestDto(productId, 5);
        CartItemResponseDto responseDto = new CartItemResponseDto(productId, "Product1", 5, 10);

        when(cartService.updateCartItem(userId, productId, requestDto)).thenReturn(responseDto);

        CartItemResponseDto result = cartController.updateCartItem(userId, productId, requestDto);

        assertEquals(responseDto.getQuantity(), result.getQuantity());
        verify(cartService, times(1)).updateCartItem(userId, productId, requestDto);
    }

    @Test
    void deleteCartItem_ShouldCallServiceAndReturnNoContent() {
        Integer productId = 1;

        ResponseEntity<Void> response = cartController.deleteCartItem(userId, productId);

        assertEquals(204, response.getStatusCode().value());
        verify(cartService, times(1)).deleteCartItem(userId, productId);
    }

    @Test
    void replaceCart_ShouldReturnReplacedCartItems() {
        CartItemRequestDto itemRequest = new CartItemRequestDto(1, 2);
        CartItemResponseDto itemResponse = new CartItemResponseDto(1, "Product1", 2, 10);

        UserPrincipal principal = new UserPrincipal(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "user",
                "password",
                Role.USER
        );

        when(authentication.getPrincipal()).thenReturn(principal);
        when(cartService.replaceCart(principal.getId().toString(), List.of(itemRequest)))
                .thenReturn(List.of(itemResponse));

        List<CartItemResponseDto> result = cartController.replaceCart(List.of(itemRequest), authentication);

        assertEquals(1, result.size());
        assertEquals(itemResponse.getProductId(), result.get(0).getProductId());
        verify(cartService, times(1)).replaceCart(principal.getId().toString(), List.of(itemRequest));
    }
}

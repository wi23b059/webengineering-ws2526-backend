package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.CartItemRequestDto;
import at.technikum.springrestbackend.dto.CartItemResponseDto;
import at.technikum.springrestbackend.entity.Cart;
import at.technikum.springrestbackend.exception.CartNotFoundException;
import at.technikum.springrestbackend.mapper.CartMapper;
import at.technikum.springrestbackend.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @MockitoBean
    private CartRepository cartRepository;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        cartService = new CartService(cartRepository);
    }

    @Test
    void testGetCart() {
        Cart cart = Cart.builder().userId("user1").productId(1).quantity(2).build();
        when(cartRepository.findByUserId("user1")).thenReturn(List.of(cart));

        List<CartItemResponseDto> result = cartService.getCart("user1");

        assertEquals(1, result.size());
        verify(cartRepository).findByUserId("user1");
    }

    @Test
    void testAddCartItem() {
        CartItemRequestDto dto = new CartItemRequestDto(1, 3);
        Cart cartEntity = CartMapper.toEntity("user1", dto);
        Cart savedCart = Cart.builder().userId("user1").productId(1).quantity(3).build();

        when(cartRepository.save(any(Cart.class))).thenReturn(savedCart);

        CartItemResponseDto response = cartService.addCartItem("user1", dto);

        assertNotNull(response);
        assertEquals(3, response.getQuantity());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void testUpdateCartItem_found() {
        Cart cart = Cart.builder().userId("user1").productId(1).quantity(2).build();
        when(cartRepository.findByUserIdAndProductId("user1", 1)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        CartItemRequestDto dto = new CartItemRequestDto(1, 5);
        CartItemResponseDto updated = cartService.updateCartItem("user1", 1, dto);

        assertEquals(5, updated.getQuantity());
        verify(cartRepository).findByUserIdAndProductId("user1", 1);
        verify(cartRepository).save(cart);
    }

    @Test
    void testUpdateCartItem_notFound() {
        when(cartRepository.findByUserIdAndProductId("user1", 1)).thenReturn(Optional.empty());
        CartItemRequestDto dto = new CartItemRequestDto(1, 5);

        assertThrows(CartNotFoundException.class,
                () -> cartService.updateCartItem("user1", 1, dto));
    }

    @Test
    void testDeleteCartItem() {
        cartService.deleteCartItem("user1", 1);
        verify(cartRepository).deleteByUserIdAndProductId("user1", 1);
    }

    @Test
    void testReplaceCart() {
        CartItemRequestDto dto1 = new CartItemRequestDto(1, 2);
        CartItemRequestDto dto2 = new CartItemRequestDto(2, 4);

        when(cartRepository.findByUserId("user1")).thenReturn(List.of(
                CartMapper.toEntity("user1", dto1),
                CartMapper.toEntity("user1", dto2)
        ));

        List<CartItemResponseDto> result = cartService.replaceCart("user1", List.of(dto1, dto2));

        assertEquals(2, result.size());
        verify(cartRepository).deleteByUserId("user1");
    }

    @Test
    void testClearCart() {
        cartService.clearCart("user1");
        verify(cartRepository).deleteByUserId("user1");
    }
}

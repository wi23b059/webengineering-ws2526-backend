package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.*;
import at.technikum.springrestbackend.entity.Order;
import at.technikum.springrestbackend.mapper.OrderItemMapper;
import at.technikum.springrestbackend.mapper.OrderMapper;
import at.technikum.springrestbackend.repository.OrderItemRepository;
import at.technikum.springrestbackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckoutServiceTest {

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private OrderItemRepository orderItemRepository;

    @MockitoBean
    private CartService cartService;

    private CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderItemRepository = mock(OrderItemRepository.class);
        cartService = mock(CartService.class);
        checkoutService = new CheckoutService(orderRepository, orderItemRepository, cartService);
    }

    @Test
    void testCheckout_success() {
        OrderRequestDto orderDto = OrderRequestDto.builder()
                .userId("user1")
                .totalPrice(BigDecimal.valueOf(100))
                .paymentMethod("CREDIT_CARD")
                .build();

        OrderItemRequestDto itemDto = new OrderItemRequestDto(1, 2, BigDecimal.valueOf(50));
        CheckoutRequestDto checkoutDto = new CheckoutRequestDto(orderDto, List.of(itemDto));

        Order savedOrder = OrderMapper.toEntity(orderDto);
        savedOrder.setId(1); // ID nach Save
        savedOrder.setOrderItems(List.of(OrderItemMapper.toEntity(savedOrder, itemDto)));

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponseDto response = checkoutService.checkout(checkoutDto);

        assertNotNull(response);
        assertEquals(orderDto.getUserId(), response.getUserId());
        assertEquals(1, response.getItems().size());

        verify(orderRepository).save(any(Order.class));
        verify(cartService).clearCart(orderDto.getUserId());
    }

    @Test
    void testCheckout_priceMismatch() {
        OrderRequestDto orderDto = OrderRequestDto.builder()
                .userId("user1")
                .totalPrice(BigDecimal.valueOf(200)) // falscher Preis
                .paymentMethod("CREDIT_CARD")
                .build();

        OrderItemRequestDto itemDto = new OrderItemRequestDto(1, 2, BigDecimal.valueOf(50));
        CheckoutRequestDto checkoutDto = new CheckoutRequestDto(orderDto, List.of(itemDto));

        assertThrows(IllegalArgumentException.class, () -> checkoutService.checkout(checkoutDto));

        verify(orderRepository, never()).save(any());
        verify(cartService, never()).clearCart(anyString());
    }
}

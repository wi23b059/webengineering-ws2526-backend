package at.technikum.springrestbackend.controller;

/**
 * Unit tests for {@link at.technikum.springrestbackend.controller.CheckoutController}.
 */

import at.technikum.springrestbackend.dto.CheckoutRequestDto;
import at.technikum.springrestbackend.dto.OrderResponseDto;
import at.technikum.springrestbackend.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CheckoutControllerTest {

    @Mock
    private CheckoutService checkoutService;

    @InjectMocks
    private CheckoutController checkoutController;

    private CheckoutRequestDto checkoutRequestDto;
    private OrderResponseDto orderResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        checkoutRequestDto = new CheckoutRequestDto();

        orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(1);
    }

    @Test
    void testCheckout() {
        when(checkoutService.checkout(checkoutRequestDto)).thenReturn(orderResponseDto);

        OrderResponseDto response = checkoutController.checkout(checkoutRequestDto);

        assertEquals(1, response.getId());
        verify(checkoutService).checkout(checkoutRequestDto);
    }
}

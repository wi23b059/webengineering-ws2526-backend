package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.CartItemRequestDto;
import at.technikum.springrestbackend.dto.CartItemResponseDto;
import at.technikum.springrestbackend.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller tests for {@link CartController}.
 * Tests web layer, validation and security behaviour.
 */
@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * CartService is mocked to isolate controller behaviour.
     */
    @MockitoBean
    private CartService cartService;

    // -------------------------------------------------------------------------
    // Security
    // -------------------------------------------------------------------------

    @Test
    void requestsAreUnauthorizedWhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/cart/user123"))
                .andExpect(status().isUnauthorized());
    }

    // -------------------------------------------------------------------------
    // GET cart
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void getCartReturnsItemsForAuthenticatedUser() throws Exception {
        CartItemResponseDto item = new CartItemResponseDto();
        // optional: setters if fields exist (id, quantity, productName, ...)

        when(cartService.getCart("user123"))
                .thenReturn(List.of(item));

        mockMvc.perform(get("/api/cart/user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // -------------------------------------------------------------------------
    // POST add item
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void addCartItemReturnsCreatedItem() throws Exception {
        CartItemRequestDto requestDto = new CartItemRequestDto();
        // Beispiel – anpassen an euer echtes DTO
        requestDto.setProductId(1);
        requestDto.setQuantity(2);

        CartItemResponseDto responseDto = new CartItemResponseDto();

        when(cartService.addCartItem(eq("user123"), any(CartItemRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/cart/user123/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void addCartItemReturnsBadRequestWhenRequestIsInvalid() throws Exception {
        CartItemRequestDto invalidRequest = new CartItemRequestDto();
        // absichtlich leer → verletzt @Valid

        mockMvc.perform(post("/api/cart/user123/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // -------------------------------------------------------------------------
    // PUT update item
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void updateCartItemReturnsUpdatedItem() throws Exception {
        CartItemRequestDto requestDto = new CartItemRequestDto();
        requestDto.setProductId(1);
        requestDto.setQuantity(3);

        CartItemResponseDto responseDto = new CartItemResponseDto();

        when(cartService.updateCartItem(
                eq("user123"),
                eq(1),
                any(CartItemRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/cart/user123/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // DELETE item
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void deleteCartItemReturnsNoContent() throws Exception {
        doNothing().when(cartService)
                .deleteCartItem("user123", 1);

        mockMvc.perform(delete("/api/cart/user123/items/1"))
                .andExpect(status().isNoContent());
    }
}

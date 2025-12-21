package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.dto.OrderItemResponseDto;
import at.technikum.springrestbackend.service.OrderItemService;
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
 * Controller tests for {@link OrderItemController}.
 * Tests web layer, validation, nested resources and security behaviour.
 */
@WebMvcTest(OrderItemController.class)
class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * OrderItemService is mocked to isolate controller behaviour.
     */
    @MockitoBean
    private OrderItemService orderItemService;

    // -------------------------------------------------------------------------
    // Security
    // -------------------------------------------------------------------------

    @Test
    void requestsAreUnauthorizedWhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/orders/1/items"))
                .andExpect(status().isUnauthorized());
    }

    // -------------------------------------------------------------------------
    // GET all order items
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void getAllOrderItemsReturnsListForAuthenticatedUser() throws Exception {
        OrderItemResponseDto item = new OrderItemResponseDto();

        when(orderItemService.getAllOrderItems(1))
                .thenReturn(List.of(item));

        mockMvc.perform(get("/api/orders/1/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // -------------------------------------------------------------------------
    // GET single order item
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void getOrderItemReturnsItemById() throws Exception {
        OrderItemResponseDto item = new OrderItemResponseDto();

        when(orderItemService.getOrderItem(1, 2))
                .thenReturn(item);

        mockMvc.perform(get("/api/orders/1/items/2"))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // POST add order item
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void addOrderItemReturnsCreatedItem() throws Exception {
        OrderItemRequestDto requestDto = new OrderItemRequestDto();
        // Beispiel – an euer echtes DTO anpassen
        // requestDto.setProductId(10);
        // requestDto.setQuantity(2);

        OrderItemResponseDto responseDto = new OrderItemResponseDto();

        when(orderItemService.addOrderItem(
                eq(1),
                any(OrderItemRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/orders/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void addOrderItemReturnsBadRequestWhenRequestIsInvalid() throws Exception {
        OrderItemRequestDto invalidRequest = new OrderItemRequestDto();
        // leer → verletzt @Valid

        mockMvc.perform(post("/api/orders/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // -------------------------------------------------------------------------
    // PUT update order item
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void updateOrderItemReturnsUpdatedItem() throws Exception {
        OrderItemRequestDto requestDto = new OrderItemRequestDto();

        OrderItemResponseDto responseDto = new OrderItemResponseDto();

        when(orderItemService.updateOrderItem(
                eq(1),
                eq(2),
                any(OrderItemRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/orders/1/items/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // DELETE order item
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void deleteOrderItemReturnsNoContent() throws Exception {
        doNothing().when(orderItemService)
                .deleteOrderItem(1, 2);

        mockMvc.perform(delete("/api/orders/1/items/2"))
                .andExpect(status().isNoContent());
    }
}

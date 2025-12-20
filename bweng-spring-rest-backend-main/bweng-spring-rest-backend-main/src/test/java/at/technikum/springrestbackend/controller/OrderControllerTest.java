package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.OrderRequestDto;
import at.technikum.springrestbackend.dto.OrderResponseDto;
import at.technikum.springrestbackend.service.OrderService;
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
 * Controller tests for {@link OrderController}.
 * Tests web layer, validation and security behaviour.
 */
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * OrderService is mocked to isolate controller behaviour.
     */
    @MockitoBean
    private OrderService orderService;

    // -------------------------------------------------------------------------
    // Security
    // -------------------------------------------------------------------------

    @Test
    void requestsAreUnauthorizedWhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isUnauthorized());
    }

    // -------------------------------------------------------------------------
    // GET all orders
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void getAllOrdersReturnsListForAuthenticatedUser() throws Exception {
        OrderResponseDto order = new OrderResponseDto();
        // optional: set fields if needed

        when(orderService.getAllOrders())
                .thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // -------------------------------------------------------------------------
    // GET single order
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void getOrderReturnsOrderById() throws Exception {
        OrderResponseDto order = new OrderResponseDto();

        when(orderService.getOrder(1))
                .thenReturn(order);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // POST create order
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void createOrderReturnsCreatedOrder() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto();
        // Beispiel – anpassen an euer echtes DTO
        // requestDto.setTotalPrice(99.99);

        OrderResponseDto responseDto = new OrderResponseDto();

        when(orderService.createOrder(any(OrderRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void createOrderReturnsBadRequestWhenRequestIsInvalid() throws Exception {
        OrderRequestDto invalidRequest = new OrderRequestDto();
        // leer → verletzt @Valid

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // -------------------------------------------------------------------------
    // PUT update order
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void updateOrderReturnsUpdatedOrder() throws Exception {
        OrderRequestDto requestDto = new OrderRequestDto();

        OrderResponseDto responseDto = new OrderResponseDto();

        when(orderService.updateOrder(
                eq(1),
                any(OrderRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    // -------------------------------------------------------------------------
    // DELETE order
    // -------------------------------------------------------------------------

    @Test
    @WithMockUser
    void deleteOrderReturnsNoContent() throws Exception {
        doNothing().when(orderService)
                .deleteOrder(1);

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }
}

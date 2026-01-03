package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.OrderItemRequestDto;
import at.technikum.springrestbackend.dto.OrderItemResponseDto;
import at.technikum.springrestbackend.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/orders/{orderId}/items")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping
    public List<OrderItemResponseDto> getAllOrderItems(@PathVariable Integer orderId) {
        return orderItemService.getAllOrderItems(orderId);
    }

    @GetMapping("/{itemId}")
    public OrderItemResponseDto getOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer itemId) {
        return orderItemService.getOrderItem(orderId, itemId);
    }

    @PostMapping
    public OrderItemResponseDto addOrderItem(
            @PathVariable Integer orderId,
            @Valid @RequestBody OrderItemRequestDto dto) {
        return orderItemService.addOrderItem(orderId, dto);
    }

    @PutMapping("/{itemId}")
    public OrderItemResponseDto updateOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer itemId,
            @Valid @RequestBody OrderItemRequestDto dto) {
        return orderItemService.updateOrderItem(orderId, itemId, dto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer itemId) {
        orderItemService.deleteOrderItem(orderId, itemId);
        return ResponseEntity.noContent().build();
    }
}

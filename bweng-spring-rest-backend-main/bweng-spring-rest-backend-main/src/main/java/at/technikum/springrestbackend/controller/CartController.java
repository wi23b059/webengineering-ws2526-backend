package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.CartItemRequestDto;
import at.technikum.springrestbackend.dto.CartItemResponseDto;
import at.technikum.springrestbackend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public List<CartItemResponseDto> getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/{userId}/items")
    public CartItemResponseDto addCartItem(@PathVariable String userId, @Valid @RequestBody CartItemRequestDto dto) {
        return cartService.addCartItem(userId, dto);
    }

    @PutMapping("/{userId}/items/{productId}")
    public CartItemResponseDto updateCartItem(
            @PathVariable String userId,
            @PathVariable Integer productId,
            @Valid @RequestBody CartItemRequestDto dto) {
        return cartService.updateCartItem(userId, productId, dto);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Void> deleteCartItem(
            @PathVariable String userId,
            @PathVariable Integer productId) {
        cartService.deleteCartItem(userId, productId);
        return ResponseEntity.noContent().build();
    }
}

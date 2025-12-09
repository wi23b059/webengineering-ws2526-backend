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

/**
 * REST controller for managing shopping carts.
 * <p>
 * Provides endpoints to view, add, update and remove items in a user's cart.
 */
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    /**
     * Returns all items in the given user's cart.
     *
     * @param userId the ID of the user whose cart should be returned
     * @return a list of cart items for the user
     */
    @GetMapping("/{userId}")
    public List<CartItemResponseDto> getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    /**
     * Adds a new item to the given user's cart.
     *
     * @param userId the ID of the user whose cart is being modified
     * @param dto the item details to add to the cart
     * @return the created cart item
     */
    @PostMapping("/{userId}/items")
    public CartItemResponseDto addCartItem(@PathVariable String userId, @Valid @RequestBody CartItemRequestDto dto) {
        return cartService.addCartItem(userId, dto);
    }

    /**
     * Updates an existing item in the user's cart.
     *
     * @param userId the ID of the user whose cart is being modified
     * @param productId the ID of the product to update in the cart
     * @param dto the updated item details (e.g. quantity)
     * @return the updated cart item
     */
    @PutMapping("/{userId}/items/{productId}")
    public CartItemResponseDto updateCartItem(
            @PathVariable String userId,
            @PathVariable Integer productId,
            @Valid @RequestBody CartItemRequestDto dto) {
        return cartService.updateCartItem(userId, productId, dto);
    }

    /**
     * Removes an item from the user's cart.
     *
     * @param userId the ID of the user whose cart is being modified
     * @param productId the ID of the product to remove from the cart
     * @return an empty 204 No Content response on success
     */
    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Void> deleteCartItem(
            @PathVariable String userId,
            @PathVariable Integer productId) {
        cartService.deleteCartItem(userId, productId);
        return ResponseEntity.noContent().build();
    }
}

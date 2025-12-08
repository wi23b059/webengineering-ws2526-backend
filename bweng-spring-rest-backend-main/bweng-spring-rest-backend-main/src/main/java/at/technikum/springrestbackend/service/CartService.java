package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.CartItemRequestDto;
import at.technikum.springrestbackend.dto.CartItemResponseDto;
import at.technikum.springrestbackend.entity.Cart;
import at.technikum.springrestbackend.exception.CartNotFoundException;
import at.technikum.springrestbackend.mapper.CartMapper;
import at.technikum.springrestbackend.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public List<CartItemResponseDto> getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .stream()
                .map(CartMapper::toResponseDto)
                .toList();
    }

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    @Transactional
    public CartItemResponseDto addCartItem(String userId, CartItemRequestDto dto) {
        Cart cart = CartMapper.toEntity(userId, dto);
        Cart saved = cartRepository.save(cart);
        return CartMapper.toResponseDto(saved);
    }

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    @Transactional
    public CartItemResponseDto updateCartItem(String userId, Integer productId, CartItemRequestDto dto) {
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new CartNotFoundException(userId));
        cart.setQuantity(dto.getQuantity());
        Cart updated = cartRepository.save(cart);
        return CartMapper.toResponseDto(updated);
    }

    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    @Transactional
    public void deleteCartItem(String userId, Integer productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }
}

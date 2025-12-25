package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    void findByUserId_shouldReturnAllCartItemsForUser() {
        // given
        Cart cart1 = Cart.builder()
                .userId("user123")
                .productId(1)
                .quantity(2)
                .build();

        Cart cart2 = Cart.builder()
                .userId("user123")
                .productId(2)
                .quantity(1)
                .build();

        Cart cartOtherUser = Cart.builder()
                .userId("otherUser")
                .productId(3)
                .quantity(5)
                .build();

        cartRepository.save(cart1);
        cartRepository.save(cart2);
        cartRepository.save(cartOtherUser);

        // when
        List<Cart> result = cartRepository.findByUserId("user123");

        // then
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> c.getUserId().equals("user123")));
    }

    @Test
    void findByUserIdAndProductId_shouldReturnMatchingCartItem() {
        // given
        Cart cart = Cart.builder()
                .userId("userABC")
                .productId(10)
                .quantity(3)
                .build();

        cartRepository.save(cart);

        // when
        Optional<Cart> result =
                cartRepository.findByUserIdAndProductId("userABC", 10);

        // then
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getQuantity());
    }

    @Test
    void findByUserIdAndProductId_shouldReturnEmptyIfNotFound() {
        // when
        Optional<Cart> result =
                cartRepository.findByUserIdAndProductId("unknown", 99);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteByUserIdAndProductId_shouldRemoveCartItem() {
        // given
        Cart cart = Cart.builder()
                .userId("deleteUser")
                .productId(7)
                .quantity(1)
                .build();

        cartRepository.save(cart);

        // when
        cartRepository.deleteByUserIdAndProductId("deleteUser", 7);

        // then
        Optional<Cart> result =
                cartRepository.findByUserIdAndProductId("deleteUser", 7);

        assertTrue(result.isEmpty());
    }
}

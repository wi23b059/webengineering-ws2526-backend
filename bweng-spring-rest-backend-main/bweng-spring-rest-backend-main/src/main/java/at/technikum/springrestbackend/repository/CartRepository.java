package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(String userId);

    Optional<Cart> findByUserIdAndProductId(String userId, Integer productId);

    void deleteByUserIdAndProductId(String userId, Integer productId);
}

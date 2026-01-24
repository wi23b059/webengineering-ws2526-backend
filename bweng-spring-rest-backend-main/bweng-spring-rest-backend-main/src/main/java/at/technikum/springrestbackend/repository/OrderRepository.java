package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(String userId);

    // 🔹 Load order together with OrderItems
    @Query("" +
            "SELECT o " +
            "FROM Order o " +
            "LEFT JOIN FETCH o.orderItems " +
            "WHERE o.id = :id")
    Optional<Order> findByIdWithItems(@Param("id") Integer id);
}

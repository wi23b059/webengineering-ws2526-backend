package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder_Id(Integer orderId);

    Optional<OrderItem> findByOrder_IdAndId(Integer orderId, Integer id);

    void deleteByOrder_IdAndId(Integer orderId, Integer id);
}

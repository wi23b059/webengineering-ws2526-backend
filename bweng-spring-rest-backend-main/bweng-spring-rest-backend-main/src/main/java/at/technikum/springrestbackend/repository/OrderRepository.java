package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(String userId);
}

package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

package spring.springbootintro.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.springbootintro.model.User;

public interface UserRepositoty extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}


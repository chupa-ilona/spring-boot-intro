package spring.springbootintro.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.springbootintro.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("SELECT sc "
            + "FROM ShoppingCart sc "
            + "LEFT JOIN FETCH sc.cartItems c "
            + "LEFT JOIN FETCH c.book "
            + "WHERE sc.user.id = :userId")
    Optional<ShoppingCart> findByUserId(Long userId);
}

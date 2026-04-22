package cart_service.repository;

import cart_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = "SELECT * FROM cart WHERE userid = :userId", nativeQuery = true)
    List<Cart> findCartsByUserId(@Param("userId") int userId);
}

package cart_service.repository;

import cart_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query(value = "SELECT * FROM cart_item WHERE cart_id = :cartId", nativeQuery = true)
    List<CartItem> findItemsByCartId(@Param("cartId") int cartId);

}

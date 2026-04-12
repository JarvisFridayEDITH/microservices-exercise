package cart_service.repository;

import cart_service.entiy.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemEntityRepository extends JpaRepository<CartItemEntity, Integer> {

}

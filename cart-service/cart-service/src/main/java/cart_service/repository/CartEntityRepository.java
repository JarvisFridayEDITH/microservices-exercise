package cart_service.repository;

import cart_service.entiy.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartEntityRepository extends JpaRepository<CartEntity, Integer> {
}

package cart_service.service;

import cart_service.entity.Cart;
import cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(int id) {
        return cartRepository.findById(id);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCart(int id) {
        cartRepository.deleteById(id);
    }
    public Page<Product> getProductsWithPagination(int page, int size, String sortBy) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

    Page<Product> productPage = productRepository.findAll(pageable);

    List<Product> filtered = productPage.getContent()
            .stream()
            .filter(p -> p.getStock() > 0)
            .map(p -> {
                p.setName(p.getName().toUpperCase());
                return p;
            })
            .toList();

    return new PageImpl<>(filtered, pageable, filtered.size());
   }

}

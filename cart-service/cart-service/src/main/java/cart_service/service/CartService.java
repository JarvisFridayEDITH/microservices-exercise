package cart_service.service;

import cart_service.entity.Cart;
import cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    // Existing methods
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

    //  Pagination + Sorting + Streams (FILTER + MAP)
    public Page<Cart> getCartsWithPagination(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Cart> cartPage = cartRepository.findAll(pageable);

        List<Cart> filtered = cartPage.getContent()
                .stream()
                .filter(c -> c.getUserid() > 0)   // filtering
                .map(c -> {
                    // transformation (simple example)
                    c.setUserid(c.getUserid());
                    return c;
                })
                .toList();

        return new PageImpl<>(filtered, pageable, filtered.size());
    }
}
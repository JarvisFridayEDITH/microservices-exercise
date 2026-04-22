package cart_service.service;

import cart_service.client.ProductClient;
import cart_service.dto.ProductResponse;
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

    @Autowired
    private ProductClient productClient;

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

    // Validates product exists before adding to cart
    public Cart addToCart(Cart cart, int productId) {

        // Step 1: Call Product Service
        ProductResponse product = productClient.getProductById(productId);

        // Step 2: Validate product exists
        if (product == null) {
            throw new RuntimeException("Cannot create cart. Product not found with ID: "
                    + productId);
        }

        return cartRepository.save(cart);
    }

    public Page<Cart> getCartsWithPagination(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Cart> cartPage = cartRepository.findAll(pageable);
        List<Cart> filtered = cartPage.getContent()
                .stream()
                .filter(c -> c.getUserid() > 0)
                .map(c -> {
                    c.setUserid(c.getUserid());
                    return c;
                })
                .toList();
        return new PageImpl<>(filtered, pageable, filtered.size());
    }

    public List<Cart> getCartsByUserId(int userId) {
        return cartRepository.findCartsByUserId(userId);
    }

    public Cart updateCart(int id, Cart cart) {
        Cart existing = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
        existing.setUserid(cart.getUserid());
        return cartRepository.save(existing);
    }
}
package cart_service.service;

import cart_service.client.ProductClient;
import cart_service.dto.ProductResponse;
import cart_service.entity.Cart;
import cart_service.entity.CartItem;
import cart_service.repository.CartRepository;
import cart_service.service.CartItemService;
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

    @Autowired
    private CartItemService cartItemService;

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

    // Updated: Now creates a CartItem with full validation (existence + stock)
    public CartItem addToCart(CartItem cartItem) {
        // Delegate to CartItemService for validation and saving
        return cartItemService.saveCartItem(cartItem);
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

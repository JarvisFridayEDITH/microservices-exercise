package cart_service.service;

import cart_service.client.ProductClient;
import cart_service.dto.ProductResponse;
import cart_service.entity.CartItem;
import cart_service.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductClient productClient;

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public Optional<CartItem> getCartItemById(int id) {
        return cartItemRepository.findById(id);
    }


    // Validates product exists + stock sufficient before saving
    public CartItem saveCartItem(CartItem cartItem) {

        // Step 1: Call Product Service
        ProductResponse product = productClient.getProductById(cartItem.getProductId());

        // Step 2: Validate product exists
        if (product == null) {
            throw new RuntimeException("Product not found with ID: "
                    + cartItem.getProductId());
        }

        // Step 3: Validate stock is sufficient
        if (product.getStock() < cartItem.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: "
                    + product.getName()
                    + ". Requested: " + cartItem.getQuantity()
                    + ", Available: " + product.getStock());
        }

        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(int id) {
        cartItemRepository.deleteById(id);
    }

    public Page<CartItem> getCartItemsWithPagination(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<CartItem> cartItemPage = cartItemRepository.findAll(pageable);
        List<CartItem> filtered = cartItemPage.getContent()
                .stream()
                .filter(item -> item.getQuantity() > 0)
                .map(item -> {
                    item.setQuantity(item.getQuantity() * 1);
                    return item;
                })
                .toList();
        return new PageImpl<>(filtered, pageable, filtered.size());
    }

    public List<CartItem> getItemsByCartId(int cartId) {
        return cartItemRepository.findItemsByCartId(cartId);
    }

    public CartItem updateCartItem(int id, CartItem cartItem) {
        CartItem existing = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found: " + id));
        existing.setCartId(cartItem.getCartId());
        existing.setProductId(cartItem.getProductId());
        existing.setQuantity(cartItem.getQuantity());
        return cartItemRepository.save(existing);
    }
}
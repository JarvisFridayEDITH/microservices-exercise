package cart_service.service;

import cart_service.client.ProductClient;
import cart_service.dto.CartEvent;
import cart_service.dto.ProductResponse;
import cart_service.entity.CartItem;
import cart_service.kafka.CartEventProducer;
import cart_service.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartEventProducer cartEventProducer;

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public Optional<CartItem> getCartItemById(int id) {
        return cartItemRepository.findById(id);
    }

    // 1K - Async parallel execution using CompletableFuture
    public CartItem saveCartItem(CartItem cartItem) {

        // Step 1: Run fetch and validation IN PARALLEL
        CompletableFuture<ProductResponse> fetchProduct = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching product on thread: "
                    + Thread.currentThread().getName());
            return productClient.getProductById(cartItem.getProductId());
        });

        CompletableFuture<Void> validateStock = fetchProduct.thenAcceptAsync(product -> {
            System.out.println("Validating stock on thread: "
                    + Thread.currentThread().getName());

            // Validate product exists
            if (product == null) {
                throw new RuntimeException("Product not found with ID: "
                        + cartItem.getProductId());
            }

            // Validate stock
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: "
                        + product.getName()
                        + ". Requested: " + cartItem.getQuantity()
                        + ", Available: " + product.getStock());
            }
        });

        // Step 2: Wait for both to complete
        try {
            CompletableFuture.allOf(fetchProduct, validateStock).join();
        } catch (Exception e) {
            throw new RuntimeException(e.getCause().getMessage());
        }

        // Step 3: Save cart item
        CartItem saved = cartItemRepository.save(cartItem);

        // Step 4: Publish Kafka event
        CartEvent event = new CartEvent(
                cartItem.getCartId(),
                cartItem.getProductId(),
                cartItem.getQuantity()
        );
        cartEventProducer.sendCartEvent(event);

        return saved;
    }

    // 1K - Async method to fetch product details
    @Async("taskExecutor")
    public CompletableFuture<ProductResponse> fetchProductAsync(int productId) {
        System.out.println("Async fetch on thread: " + Thread.currentThread().getName());
        ProductResponse product = productClient.getProductById(productId);
        return CompletableFuture.completedFuture(product);
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
package cart_service.controller;

import cart_service.entity.Cart;
import cart_service.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> getAllCarts() {
        log.info("GET /cart - fetching all carts");
        return cartService.getAllCarts();
    }

    @GetMapping("/paged")
    public Page<Cart> getCartsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {
        log.info("GET /cart/paged - page={}, size={}, sortBy={}", page, size, sortBy);
        return cartService.getCartsWithPagination(page, size, sortBy);
    }

    @GetMapping("/by-user/{userId}")
    public List<Cart> getCartsByUserId(@PathVariable int userId) {
        log.info("GET /cart/by-user/{} - fetching carts", userId);
        return cartService.getCartsByUserId(userId);
    }

    @GetMapping("/{id}")
    public Optional<Cart> getCartById(@PathVariable int id) {
        log.info("GET /cart/{} - fetching cart", id);
        return cartService.getCartById(id);
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        log.info("POST /cart - creating cart for userId: {}", cart.getUserid());
        return cartService.saveCart(cart);
    }

    @PostMapping("/add-to-cart/{productId}")
    public Cart addToCart(@RequestBody Cart cart, @PathVariable int productId) {
        log.info("POST /cart/add-to-cart/{} - adding to cart", productId);
        return cartService.addToCart(cart, productId);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable int id, @RequestBody Cart cart) {
        log.info("PUT /cart/{} - updating cart", id);
        return cartService.updateCart(id, cart);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable int id) {
        log.info("DELETE /cart/{} - deleting cart", id);
        cartService.deleteCart(id);
    }
}
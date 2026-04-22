package cart_service.controller;

import cart_service.entity.Cart;
import cart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // GET all carts
    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/paged")
    public Page<Cart> getCartsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy
    ) {
        return cartService.getCartsWithPagination(page, size, sortBy);
    }
    // Add to existing CartController
    @GetMapping("/by-user/{userId}")
    public List<Cart> getCartsByUserId(@PathVariable int userId) {
        return cartService.getCartsByUserId(userId);
    }
    // Add this endpoint to existing CartController
    @PostMapping("/add-to-cart/{productId}")
    public Cart addToCart(@RequestBody Cart cart, @PathVariable int productId) {
        return cartService.addToCart(cart, productId);
    }

    // Also fix the PUT method
    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable int id, @RequestBody Cart cart) {
        return cartService.updateCart(id, cart);
    }

    // GET cart by ID
    @GetMapping("/{id}")
    public Optional<Cart> getCartById(@PathVariable int id) {
        return cartService.getCartById(id);
    }

    // CREATE cart
    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart);
    }


    // DELETE cart
    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable int id) {
        cartService.deleteCart(id);
    }
}
package cart_service.controller;

import cart_service.entity.CartItem;
import cart_service.service.CartItemService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart-item")
public class CartItemController {

    private static final Logger log = LoggerFactory.getLogger(CartItemController.class);

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public List<CartItem> getAllCartItems() {
        log.info("GET /cart-item - fetching all cart items");
        return cartItemService.getAllCartItems();
    }

    @GetMapping("/paged")
    public Page<CartItem> getCartItemsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {
        log.info("GET /cart-item/paged - page={}, size={}, sortBy={}", page, size, sortBy);
        return cartItemService.getCartItemsWithPagination(page, size, sortBy);
    }

    @GetMapping("/by-cart/{cartId}")
    public List<CartItem> getItemsByCartId(@PathVariable int cartId) {
        log.info("GET /cart-item/by-cart/{} - fetching items", cartId);
        return cartItemService.getItemsByCartId(cartId);
    }

    @GetMapping("/{id}")
    public Optional<CartItem> getCartItemById(@PathVariable int id) {
        log.info("GET /cart-item/{} - fetching cart item", id);
        return cartItemService.getCartItemById(id);
    }

    @PostMapping
    public CartItem createCartItem(@Valid @RequestBody CartItem cartItem) {
        log.info("POST /cart-item - creating cart item: {}", cartItem);
        return cartItemService.saveCartItem(cartItem);
    }

    @PutMapping("/{id}")
    public CartItem updateCartItem(@PathVariable int id, @Valid @RequestBody CartItem cartItem) {
        log.info("PUT /cart-item/{} - updating cart item", id);
        return cartItemService.updateCartItem(id, cartItem);
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem(@PathVariable int id) {
        log.info("DELETE /cart-item/{} - deleting cart item", id);
        cartItemService.deleteCartItem(id);
    }
}
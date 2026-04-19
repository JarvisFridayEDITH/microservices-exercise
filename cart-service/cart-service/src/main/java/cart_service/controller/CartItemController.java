package cart_service.controller;

import cart_service.entity.CartItem;
import cart_service.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart-item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemService.getAllCartItems();
    }
    @GetMapping("/paged")
    public Page<CartItem> getCartItemsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy
    ) {
        return cartItemService.getCartItemsWithPagination(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Optional<CartItem> getCartItemById(@PathVariable int id) {
        return cartItemService.getCartItemById(id);
    }

    @PostMapping
    public CartItem createCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.saveCartItem(cartItem);
    }

    @PutMapping("/{id}")
    public CartItem updateCartItem(@PathVariable int id, @RequestBody CartItem cartItem) {
        cartItem.setId(id);
        return cartItemService.saveCartItem(cartItem);
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem(@PathVariable int id) {
        cartItemService.deleteCartItem(id);
    }
}
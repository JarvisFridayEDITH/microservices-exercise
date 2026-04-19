package cart_service.service;

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

    // Existing methods
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    public Optional<CartItem> getCartItemById(int id) {
        return cartItemRepository.findById(id);
    }

    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(int id) {
        cartItemRepository.deleteById(id);
    }

    //  Pagination + Sorting + Streams (FILTER + MAP)
    public Page<CartItem> getCartItemsWithPagination(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<CartItem> cartItemPage = cartItemRepository.findAll(pageable);

        List<CartItem> filtered = cartItemPage.getContent()
                .stream()
                .filter(item -> item.getQuantity() > 0)   // filtering
                .map(item -> {
                    // transformation (simple example)
                    item.setQuantity(item.getQuantity() * 1);
                    return item;
                })
                .toList();

        return new PageImpl<>(filtered, pageable, filtered.size());
    }
}
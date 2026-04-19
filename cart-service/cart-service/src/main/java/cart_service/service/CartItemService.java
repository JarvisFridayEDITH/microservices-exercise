package cart_service.service;

import cart_service.entity.CartItem;
import cart_service.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;


import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;


    public Page<CartItem> getCartItemsWithPagination(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<CartItem> cartItemPage = cartItemRepository.findAll(pageable);

        // Java Streams
        List<CartItem> filtered = cartItemPage.getContent()
                .stream()
                .filter(item -> item.getQuantity() > 0)
                .toList();

        return new PageImpl<>(filtered, pageable, filtered.size());
    }

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


}

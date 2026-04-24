package cart_service.dto;

public class CartEvent {

    private int cartId;
    private int productId;
    private int quantity;

    public CartEvent() {}

    public CartEvent(int cartId, int productId, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "CartEvent{cartId=" + cartId +
                ", productId=" + productId +
                ", quantity=" + quantity + "}";
    }
}
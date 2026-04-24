package cart_service.kafka;

import cart_service.dto.CartEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CartEventProducer {

    private static final String TOPIC = "cart-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendCartEvent(CartEvent event) {
        try {
            String message = "{\"cartId\":" + event.getCartId()
                    + ",\"productId\":" + event.getProductId()
                    + ",\"quantity\":" + event.getQuantity() + "}";

            kafkaTemplate.send(TOPIC, message);
            System.out.println("Kafka event sent: " + message);
        } catch (Exception e) {
            System.err.println("Failed to send Kafka event: " + e.getMessage());
        }
    }
}
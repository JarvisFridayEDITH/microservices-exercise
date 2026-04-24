package cart_service.kafka;

import cart_service.dto.CartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CartEventProducer {

    private static final Logger log = LoggerFactory.getLogger(CartEventProducer.class);
    private static final String TOPIC = "cart-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendCartEvent(CartEvent event) {
        try {
            String message = "{\"cartId\":" + event.getCartId()
                    + ",\"productId\":" + event.getProductId()
                    + ",\"quantity\":" + event.getQuantity() + "}";

            kafkaTemplate.send(TOPIC, message);
            log.info("Kafka event sent to topic {}: {}", TOPIC, message);
        } catch (Exception e) {
            log.error("Failed to send Kafka event: {}", e.getMessage());
        }
    }
}
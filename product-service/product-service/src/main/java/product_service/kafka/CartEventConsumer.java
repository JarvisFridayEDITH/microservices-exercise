package product_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CartEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(CartEventConsumer.class);

    @KafkaListener(topics = "cart-events", groupId = "product-service-group")
    public void consumeCartEvent(String message) {
        log.info("====================================");
        log.info("Kafka event received: {}", message);
        log.info("====================================");
    }
}
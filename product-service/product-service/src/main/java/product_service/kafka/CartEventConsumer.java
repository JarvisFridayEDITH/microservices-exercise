package product_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CartEventConsumer {

    @KafkaListener(topics = "cart-events", groupId = "product-service-group")
    public void consumeCartEvent(String message) {
        System.out.println("====================================");
        System.out.println("Kafka event received: " + message);
        System.out.println("====================================");
    }
}
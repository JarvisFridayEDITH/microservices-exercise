package cart_service.client;

import cart_service.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class ProductClient {

    @Autowired
    private WebClient webClient;

    // Fetch product by ID from Product Service
    public ProductResponse getProductById(int productId) {
        try {
            return webClient.get()
                    .uri("/V1/{id}", productId)
                    .retrieve()
                    .bodyToMono(ProductResponse.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null; // product not found
        }
    }
}
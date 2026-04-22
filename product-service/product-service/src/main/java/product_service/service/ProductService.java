package product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import product_service.entity.Product;
import product_service.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProductsWithPagination(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Product> productPage = productRepository.findAll(pageable);

        List<Product> filtered = productPage.getContent()
                .stream()
                .filter(p -> p.getStock() > 0)
                .peek(p -> p.setName(p.getName().toUpperCase()))
                .toList();

        return new PageImpl<>(filtered, pageable, productPage.getTotalElements());
    }
    // Add this method to your existing ProductService class
    public List<Product> getProductsAbovePrice(Double minPrice) {
        return productRepository.findProductsAbovePrice(minPrice);
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

}

package product_service.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product_service.entity.Product;
import product_service.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/V1")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        log.info("GET /V1 - fetching all products");
        return productService.getAllProducts();
    }

    @GetMapping("/paged")
    public Page<Product> getProductsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {
        log.info("GET /V1/paged - page={}, size={}, sortBy={}", page, size, sortBy);
        return productService.getProductsWithPagination(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        log.info("GET /V1/{} - fetching product", id);
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product) {
        log.info("POST /V1/products - creating product: {}", product.getName());
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @Valid @RequestBody Product product) {
        log.info("PUT /V1/{} - updating product", id);
        product.setId(id);
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        log.info("DELETE /V1/{} - deleting product", id);
        productService.deleteProduct(id);
    }

    @GetMapping("/above-price")
    public List<Product> getProductsAbovePrice(@RequestParam Double minPrice) {
        log.info("GET /V1/above-price?minPrice={}", minPrice);
        return productService.getProductsAbovePrice(minPrice);
    }
}
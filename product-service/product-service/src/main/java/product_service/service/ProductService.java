package product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import product_service.entity.Product;
import product_service.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Map<String, Object>> getAllProducts(Pageable pageable, String name) {
        List<Product> all = productRepository.findAll(pageable.getSort());
        List<Product> filtered = all.stream()
                .filter(p -> name == null || (p.getName() != null && p.getName().toLowerCase().contains(name.toLowerCase())))
                .collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<Product> pageContent = filtered.subList(start, end);
        List<Map<String, Object>> transformed = pageContent.stream()
                .map(p -> Map.of("id", p.getId(), "name", p.getName().toUpperCase(), "price", p.getPrice(), "stock", p.getStock()))
                .collect(Collectors.toList());
        return new PageImpl<>(transformed, pageable, filtered.size());
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

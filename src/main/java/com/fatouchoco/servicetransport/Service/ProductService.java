package com.fatouchoco.servicetransport.Service;

import com.fatouchoco.servicetransport.Model.Product;
import com.fatouchoco.servicetransport.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final SecureRandom random = new SecureRandom();

    public Product addProduct(Product product) {
        product.setProductId(generateProductId());
        LocalDate  date = LocalDate.now();

        product.setDate(date);

        return productRepository.save(product);
    }

    public void updateStatus(Long id, String status) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setStatus(status);
            productRepository.save(product);
        }
    }


    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private String generateProductId() {
        int pin = random.nextInt(1000000);
        return String.format("%06d", pin); // Returns a 6-digit random PIN
    }
}

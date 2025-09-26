package com.marketplace.productservice.service;

import com.marketplace.productservice.model.Product;
import com.marketplace.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findByActiveTrue();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findByIdAndActiveTrue(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryAndActiveTrue(category);
    }

    public List<Product> getProductsBySeller(Long sellerId) {
        return productRepository.findBySellerIdAndActiveTrue(sellerId);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseAndActiveTrue(keyword);
    }

    public Product createProduct(Product product) {
        // Generate SKU if not provided
        if (product.getSku() == null || product.getSku().isEmpty()) {
            product.setSku(generateSKU(product.getName()));
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());
            product.setCategory(productDetails.getCategory());
            product.setImageUrl(productDetails.getImageUrl());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresent(product -> {
            product.setActive(false);
            productRepository.save(product);
        });
    }

    public boolean updateStock(Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (product.getStockQuantity() >= quantity) {
                product.setStockQuantity(product.getStockQuantity() - quantity);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }

    private String generateSKU(String productName) {
        String base = productName.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        String timestamp = String.valueOf(System.currentTimeMillis());
        return base.substring(0, Math.min(base.length(), 8)) + timestamp.substring(timestamp.length() - 4);
    }
}
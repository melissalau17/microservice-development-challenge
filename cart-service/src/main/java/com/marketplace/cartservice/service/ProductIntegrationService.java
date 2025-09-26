package com.marketplace.cartservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ProductIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    private final String productServiceUrl = "http://product-service:8082";

    public ProductInfo getProductById(Long productId) {
        try {
            String url = productServiceUrl + "/api/products/" + productId;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response != null) {
                ProductInfo productInfo = new ProductInfo();
                productInfo.setId(((Number) response.get("id")).longValue());
                productInfo.setName((String) response.get("name"));
                productInfo.setPrice(new java.math.BigDecimal(response.get("price").toString()));
                productInfo.setStockQuantity(((Number) response.get("stockQuantity")).intValue());
                return productInfo;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch product information: " + e.getMessage());
        }
        throw new RuntimeException("Product not found with id: " + productId);
    }

    public static class ProductInfo {
        private Long id;
        private String name;
        private java.math.BigDecimal price;
        private Integer stockQuantity;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public java.math.BigDecimal getPrice() { return price; }
        public void setPrice(java.math.BigDecimal price) { this.price = price; }

        public Integer getStockQuantity() { return stockQuantity; }
        public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    }
}
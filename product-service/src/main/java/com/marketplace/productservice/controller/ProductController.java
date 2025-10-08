package com.marketplace.productservice.controller;

import com.marketplace.productservice.dto.CreateProductRequest;
import com.marketplace.productservice.dto.ProductDTO;
import com.marketplace.productservice.model.Product;
import com.marketplace.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(convertToDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<ProductDTO> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/seller/{sellerId}")
    public List<ProductDTO> getProductsBySeller(@PathVariable Long sellerId) {
        return productService.getProductsBySeller(sellerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(@RequestParam String q) {
        return productService.searchProducts(q).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody CreateProductRequest request) {
        Product product = new Product(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getStockQuantity(),
            request.getCategory(),
            request.getSellerId()
        );
        product.setImageUrl(request.getImageUrl());
        
        Product savedProduct = productService.createProduct(product);
        return convertToDTO(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductRequest request) {
        try {
            Product productDetails = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity(),
                request.getCategory(),
                request.getSellerId()
            );
            productDetails.setImageUrl(request.getImageUrl());
            
            Product updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(convertToDTO(updatedProduct));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());
        dto.setSku(product.getSku());
        dto.setSellerId(product.getSellerId());
        dto.setActive(product.isActive());
        dto.setCreatedAt(product.getCreatedAt());
        return dto;
    }
}
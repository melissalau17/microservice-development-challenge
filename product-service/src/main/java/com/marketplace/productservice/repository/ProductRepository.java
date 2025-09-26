package com.marketplace.productservice.repository;

import com.marketplace.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    List<Product> findByCategoryAndActiveTrue(String category);
    List<Product> findBySellerIdAndActiveTrue(Long sellerId);
    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.active = true")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
    
    Optional<Product> findByIdAndActiveTrue(Long id);
    long countBySellerId(Long sellerId);
}
package com.marketplace.cartservice.repository;

import com.marketplace.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndStatus(Long userId, Cart.CartStatus status);
    Optional<Cart> findByUserId(Long userId);
}
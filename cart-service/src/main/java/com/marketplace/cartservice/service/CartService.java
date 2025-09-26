package com.marketplace.cartservice.service;

import com.marketplace.cartservice.model.Cart;
import com.marketplace.cartservice.model.CartItem;
import com.marketplace.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductIntegrationService productIntegrationService;

    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserIdAndStatus(userId, Cart.CartStatus.ACTIVE)
                .orElseGet(() -> {
                    Cart newCart = new Cart(userId);
                    return cartRepository.save(newCart);
                });
    }

    public Cart addToCart(Long userId, Long productId, Integer quantity) {
        // Get product details from Product Service
        ProductIntegrationService.ProductInfo product = productIntegrationService.getProductById(productId);

        Cart cart = getOrCreateCart(userId);

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity
            CartItem item = existingItem.get();
            item.updateQuantity(item.getQuantity() + quantity);
        } else {
            // Add new item
            CartItem newItem = new CartItem(
                productId,
                product.getName(),
                product.getPrice(),
                quantity
            );
            cart.addItem(newItem);
        }

        return cartRepository.save(cart);
    }

    // ... rest of the methods remain the same
    public Cart updateCartItem(Long userId, Long itemId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        
        cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .ifPresent(item -> {
                    if (quantity <= 0) {
                        cart.removeItem(item);
                    } else {
                        item.updateQuantity(quantity);
                    }
                });

        return cartRepository.save(cart);
    }

    public String checkout(Long userId) {
        Cart cart = getCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot checkout empty cart");
        }
        
        String orderId = "ORD-" + System.currentTimeMillis();
        
        clearCart(userId);
        
        return orderId;
    }

    public Cart removeFromCart(Long userId, Long itemId) {
        Cart cart = getOrCreateCart(userId);
        
        cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .ifPresent(cart::removeItem);

        return cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.clearCart();
        cartRepository.save(cart);
    }

    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
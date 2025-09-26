package com.marketplace.cartservice.controller;

import com.marketplace.cartservice.dto.AddToCartRequest;
import com.marketplace.cartservice.dto.CartDTO;
import com.marketplace.cartservice.dto.CartItemDTO;
import com.marketplace.cartservice.model.Cart;
import com.marketplace.cartservice.model.CartItem;
import com.marketplace.cartservice.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart Management", description = "APIs for shopping cart operations")
public class CartController {

    @Autowired
    private CartService cartService;

    @Operation(
        summary = "Get user cart",
        description = "Retrieve the shopping cart for a specific user"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Cart not found for user"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDTO> getCart(
        @Parameter(description = "User ID", example = "1")
        @PathVariable Long userId) {
        
        return cartService.getCartByUserId(userId)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Add item to cart",
        description = "Add a product to user's shopping cart"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User or product not found")
    })
    @PostMapping("/user/{userId}/items")
    public ResponseEntity<CartDTO> addToCart(
        @Parameter(description = "User ID", example = "1")
        @PathVariable Long userId,
        
        @Valid @RequestBody AddToCartRequest request) {
        
        Cart cart = cartService.addToCart(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(convertToDTO(cart));
    }

    @Operation(
        summary = "Update cart item quantity",
        description = "Update the quantity of a specific item in the cart"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid quantity"),
        @ApiResponse(responseCode = "404", description = "Cart or item not found")
    })
    @PutMapping("/user/{userId}/items/{itemId}")
    public ResponseEntity<CartDTO> updateCartItem(
        @Parameter(description = "User ID", example = "1")
        @PathVariable Long userId,
        
        @Parameter(description = "Cart item ID", example = "5")
        @PathVariable Long itemId,
        
        @Parameter(description = "New quantity", example = "3")
        @RequestParam Integer quantity) {
        
        Cart cart = cartService.updateCartItem(userId, itemId, quantity);
        return ResponseEntity.ok(convertToDTO(cart));
    }

    @Operation(
        summary = "Remove item from cart",
        description = "Remove a specific item from user's cart"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item removed successfully"),
        @ApiResponse(responseCode = "404", description = "Cart or item not found")
    })
    @DeleteMapping("/user/{userId}/items/{itemId}")
    public ResponseEntity<CartDTO> removeFromCart(
        @Parameter(description = "User ID", example = "1")
        @PathVariable Long userId,
        
        @Parameter(description = "Cart item ID", example = "5")
        @PathVariable Long itemId) {
        
        Cart cart = cartService.removeFromCart(userId, itemId);
        return ResponseEntity.ok(convertToDTO(cart));
    }

    @Operation(
        summary = "Clear entire cart",
        description = "Remove all items from user's shopping cart"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cart cleared successfully"),
        @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<Void> clearCart(
        @Parameter(description = "User ID", example = "1")
        @PathVariable Long userId) {
        
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Checkout cart",
        description = "Process checkout for user's cart and create order"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkout successful"),
        @ApiResponse(responseCode = "400", description = "Cart is empty or invalid"),
        @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @PostMapping("/user/{userId}/checkout")
    public ResponseEntity<String> checkout(
        @Parameter(description = "User ID", example = "1")
        @PathVariable Long userId) {
        
        try {
            String orderId = cartService.checkout(userId);
            return ResponseEntity.ok("Checkout successful. Order ID: " + orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Checkout failed: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Get cart summary",
        description = "Get brief summary of user's cart (item count, total amount)"
    )
    @ApiResponse(responseCode = "200", description = "Summary retrieved successfully")
    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<CartSummaryDTO> getCartSummary(
        @Parameter(description = "User ID", example = "1")
        @PathVariable Long userId) {
        
        return cartService.getCartByUserId(userId)
                .map(this::convertToSummaryDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Helper method to convert Entity to DTO
    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setTotalAmount(cart.getTotalAmount());
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        dto.setStatus(cart.getStatus().name());

        dto.setItems(cart.getItems().stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private CartItemDTO convertItemToDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setProductName(item.getProductName());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getSubtotal());
        dto.setAddedAt(item.getAddedAt());
        return dto;
    }

    private CartSummaryDTO convertToSummaryDTO(Cart cart) {
        CartSummaryDTO summary = new CartSummaryDTO();
        summary.setUserId(cart.getUserId());
        summary.setTotalItems(cart.getItems().size());
        summary.setTotalAmount(cart.getTotalAmount());
        summary.setItemCount(cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum());
        return summary;
    }

    // Inner DTO class for cart summary
    public static class CartSummaryDTO {
        private Long userId;
        private int totalItems;
        private int itemCount;
        private Double totalAmount;

        // Getters and Setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public int getTotalItems() { return totalItems; }
        public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

        public int getItemCount() { return itemCount; }
        public void setItemCount(int itemCount) { this.itemCount = itemCount; }

        public Double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    }
}
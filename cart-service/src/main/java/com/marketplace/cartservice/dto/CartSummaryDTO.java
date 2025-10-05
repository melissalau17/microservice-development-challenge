package com.marketplace.cartservice.dto;
import java.math.BigDecimal;

public class CartSummaryDTO {
    private Long userId;
    private int totalItems;
    private int itemCount;
    private BigDecimal totalAmount;

    public CartSummaryDTO() {}

    public CartSummaryDTO(Long userId, int totalItems, int itemCount, BigDecimal totalAmount) {
        this.userId = userId;
        this.totalItems = totalItems;
        this.itemCount = itemCount;
        this.totalAmount = totalAmount;
    }

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
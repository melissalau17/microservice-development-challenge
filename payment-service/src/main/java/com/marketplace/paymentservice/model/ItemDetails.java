package com.marketplace.paymentservice.model;

import java.math.BigDecimal;

public class ItemDetails {
    private String id;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    // Constructors, Getters, Setters
    public ItemDetails() {}

    public ItemDetails(String id, String name, BigDecimal price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
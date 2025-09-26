package com.marketplace.paymentservice.model;

import java.math.BigDecimal;

public class PaymentRequest {
    private Long orderId;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod; // CREDIT_CARD, BANK_TRANSFER, E-WALLET
    private CustomerDetails customerDetails;
    private ItemDetails[] itemDetails;

    // Constructors
    public PaymentRequest() {}

    public PaymentRequest(Long orderId, BigDecimal amount, String paymentMethod) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.currency = "IDR";
    }

    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public CustomerDetails getCustomerDetails() { return customerDetails; }
    public void setCustomerDetails(CustomerDetails customerDetails) { this.customerDetails = customerDetails; }

    public ItemDetails[] getItemDetails() { return itemDetails; }
    public void setItemDetails(ItemDetails[] itemDetails) { this.itemDetails = itemDetails; }
}
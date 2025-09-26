package com.marketplace.paymentservice.model;

public class PaymentResponse {
    private String paymentId;
    private String status; // PENDING, SUCCESS, FAILED
    private String message;
    private String redirectUrl;
    private String transactionTime;

    // Constructors
    public PaymentResponse() {}

    public PaymentResponse(String paymentId, String status, String message) {
        this.paymentId = paymentId;
        this.status = status;
        this.message = message;
        this.transactionTime = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRedirectUrl() { return redirectUrl; }
    public void setRedirectUrl(String redirectUrl) { this.redirectUrl = redirectUrl; }

    public String getTransactionTime() { return transactionTime; }
    public void setTransactionTime(String transactionTime) { this.transactionTime = transactionTime; }
}
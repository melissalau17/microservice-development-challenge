package com.marketplace.paymentservice.controller;

import com.marketplace.paymentservice.model.PaymentRequest;
import com.marketplace.paymentservice.model.PaymentResponse;
import com.marketplace.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.processPayment(paymentRequest);
    }

    @GetMapping("/status/{paymentId}")
    public PaymentResponse checkPaymentStatus(@PathVariable String paymentId) {
        return paymentService.checkPaymentStatus(paymentId);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Payment Service is running! (Mock Mode)";
    }

    @GetMapping("/methods")
    public String[] getAvailablePaymentMethods() {
        return new String[] {
            "CREDIT_CARD", 
            "BANK_TRANSFER", 
            "E-WALLET", 
            "QR_CODE"
        };
    }
}
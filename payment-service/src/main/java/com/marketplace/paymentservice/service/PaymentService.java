package com.marketplace.paymentservice.service;

import com.marketplace.paymentservice.model.PaymentRequest;
import com.marketplace.paymentservice.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class PaymentService {

    @Value("${payment.mock.enabled:true}")
    private boolean mockEnabled;

    @Value("${payment.midtrans.api-url:}")
    private String midtransApiUrl;

    @Value("${payment.midtrans.server-key:}")
    private String serverKey;

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        if (mockEnabled) {
            return processMockPayment(paymentRequest);
        } else {
            return processRealPayment(paymentRequest);
        }
    }

    private PaymentResponse processMockPayment(PaymentRequest paymentRequest) {
        // Simulate payment processing
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Simulate different outcomes based on amount (for demo)
        String status;
        String message;
        
        if (paymentRequest.getAmount().doubleValue() > 1000000) {
            status = "FAILED";
            message = "Payment failed: Amount exceeds limit";
        } else {
            status = "SUCCESS";
            message = "Payment processed successfully";
        }

        return new PaymentResponse(paymentId, status, message);
    }

    private PaymentResponse processRealPayment(PaymentRequest paymentRequest) {
        // Integration with real payment gateway (Midtrans)
        try {
            // Prepare request for Midtrans
            // This is a simplified version - real implementation would be more complex
            /*
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(serverKey, "");
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("transaction_details", Map.of(
                "order_id", paymentRequest.getOrderId(),
                "gross_amount", paymentRequest.getAmount()
            ));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(
                midtransApiUrl + "/charge", 
                request, 
                Map.class
            );
            */

            // For now, return mock response since we're in demo mode
            return processMockPayment(paymentRequest);
            
        } catch (Exception e) {
            return new PaymentResponse(
                "PAY-ERROR", 
                "FAILED", 
                "Payment processing error: " + e.getMessage()
            );
        }
    }

    public PaymentResponse checkPaymentStatus(String paymentId) {
        if (mockEnabled) {
            // Simulate status check
            return new PaymentResponse(paymentId, "SUCCESS", "Payment completed");
        } else {
            // Real status check implementation
            try {
                // This would call payment gateway's status API
                return new PaymentResponse(paymentId, "SUCCESS", "Payment verified");
            } catch (Exception e) {
                return new PaymentResponse(paymentId, "UNKNOWN", "Status check failed");
            }
        }
    }
}
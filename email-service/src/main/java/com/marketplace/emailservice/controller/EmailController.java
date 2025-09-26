package com.marketplace.emailservice.controller;

import com.marketplace.emailservice.model.EmailRequest;
import com.marketplace.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/welcome")
    public String sendWelcomeEmail(@RequestBody EmailRequest request) {
        return emailService.sendWelcomeEmail(request.getToEmail(), request.getUsername());
    }

    @PostMapping("/order-confirmation")
    public String sendOrderConfirmation(@RequestBody EmailRequest request) {
        return emailService.sendOrderConfirmation(request.getToEmail(), 
                                                request.getUsername(), 
                                                request.getBody());
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Email Service is running!";
    }
}
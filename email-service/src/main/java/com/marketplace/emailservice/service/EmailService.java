package com.marketplace.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendWelcomeEmail(String toEmail, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Welcome to Marketplace!");
            message.setText(
                "Hello " + username + "!\n\n" +
                "Your account has been created successfully.\n" +
                "Welcome to our marketplace platform!\n\n" +
                "Thank you for joining us!"
            );
            
            mailSender.send(message);
            return "Welcome email sent successfully to " + toEmail;
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }

    public String sendOrderConfirmation(String toEmail, String username, String orderDetails) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Order Confirmation");
            message.setText(
                "Dear " + username + ",\n\n" +
                "Your order has been confirmed!\n\n" +
                "Order Details:\n" + orderDetails + "\n\n" +
                "Thank you for your purchase!"
            );
            
            mailSender.send(message);
            return "Order confirmation sent to " + toEmail;
        } catch (Exception e) {
            return "Failed to send order confirmation: " + e.getMessage();
        }
    }
}
package com.ecommerce.service;

import com.ecommerce.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmationEmail(String to, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Order Confirmation - " + order.getOrderNumber());
        message.setText(buildOrderConfirmationEmailContent(order));
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send order confirmation email: " + e.getMessage());
        }
    }

    public void sendPaymentConfirmationEmail(String to, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Payment Confirmation - " + order.getOrderNumber());
        message.setText(buildPaymentConfirmationEmailContent(order));
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send payment confirmation email: " + e.getMessage());
        }
    }

    public void sendOrderStatusUpdateEmail(String to, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Order Status Update - " + order.getOrderNumber());
        message.setText(buildOrderStatusUpdateEmailContent(order));
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send order status update email: " + e.getMessage());
        }
    }

    private String buildOrderConfirmationEmailContent(Order order) {
        StringBuilder content = new StringBuilder();
        content.append("Dear ").append(order.getUser().getFirstName()).append(",\n\n");
        content.append("Thank you for your order! Your order has been received and is being processed.\n\n");
        content.append("Order Details:\n");
        content.append("Order Number: ").append(order.getOrderNumber()).append("\n");
        content.append("Order Date: ").append(order.getCreatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))).append("\n");
        content.append("Status: ").append(order.getStatus()).append("\n\n");
        
        content.append("Items:\n");
        order.getOrderItems().forEach(item -> {
            content.append("- ").append(item.getProduct().getName())
                   .append(" x").append(item.getQuantity())
                   .append(" @ $").append(item.getPrice())
                   .append(" = $").append(item.getPrice().multiply(new BigDecimal(item.getQuantity()))).append("\n");
        });
        
        content.append("\nTotals:\n");
        content.append("Subtotal: $").append(order.calculateSubtotal()).append("\n");
        content.append("Tax: $").append(order.getTaxAmount()).append("\n");
        content.append("Shipping: $").append(order.getShippingAmount()).append("\n");
        content.append("Total: $").append(order.getTotalAmount()).append("\n\n");
        
        content.append("We'll send you another email once your payment is processed.\n\n");
        content.append("Best regards,\nThe E-commerce Team");
        
        return content.toString();
    }

    private String buildPaymentConfirmationEmailContent(Order order) {
        StringBuilder content = new StringBuilder();
        content.append("Dear ").append(order.getUser().getFirstName()).append(",\n\n");
        content.append("Great news! Your payment has been processed successfully.\n\n");
        content.append("Payment Details:\n");
        content.append("Order Number: ").append(order.getOrderNumber()).append("\n");
        content.append("Payment Method: ").append(order.getPaymentMethod()).append("\n");
        content.append("Amount Paid: $").append(order.getTotalAmount()).append("\n");
        content.append("Payment Status: ").append(order.getPaymentStatus()).append("\n\n");
        
        content.append("Your order is now confirmed and will be shipped soon.\n\n");
        content.append("Best regards,\nThe E-commerce Team");
        
        return content.toString();
    }

    private String buildOrderStatusUpdateEmailContent(Order order) {
        StringBuilder content = new StringBuilder();
        content.append("Dear ").append(order.getUser().getFirstName()).append(",\n\n");
        content.append("Your order status has been updated.\n\n");
        content.append("Order Details:\n");
        content.append("Order Number: ").append(order.getOrderNumber()).append("\n");
        content.append("New Status: ").append(order.getStatus()).append("\n");
        content.append("Updated: ").append(order.getUpdatedAt().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"))).append("\n\n");
        
        content.append("Best regards,\nThe E-commerce Team");
        
        return content.toString();
    }
} 
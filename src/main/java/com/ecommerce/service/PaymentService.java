package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public boolean processPayment(Order order, String paymentIntentId) {
        try {
            // Verify the payment intent with Stripe
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            
            // Check if payment was successful
            return "succeeded".equals(paymentIntent.getStatus());
        } catch (StripeException e) {
            // Log the error
            System.err.println("Payment processing error: " + e.getMessage());
            return false;
        }
    }

    public String createPaymentIntent(Order order) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(order.getTotalAmount().multiply(new java.math.BigDecimal("100")).longValue()) // Convert to cents
                    .setCurrency("usd")
                    .setDescription("Order " + order.getOrderNumber())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return paymentIntent.getId();
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create payment intent", e);
        }
    }

    public boolean refundPayment(Order order) {
        try {
            if (order.getStripePaymentIntentId() != null) {
                PaymentIntent paymentIntent = PaymentIntent.retrieve(order.getStripePaymentIntentId());
                paymentIntent.cancel();
                return true;
            }
            return false;
        } catch (StripeException e) {
            System.err.println("Refund error: " + e.getMessage());
            return false;
        }
    }
} 
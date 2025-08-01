package com.ecommerce.service;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.entity.*;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private EmailService emailService;

    public Order createOrder(Long userId, OrderRequest orderRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate unique order number
        String orderNumber = generateOrderNumber();
        
        Order order = new Order(user, orderNumber);
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setBillingAddress(orderRequest.getBillingAddress());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setNotes(orderRequest.getNotes());

        // Process order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderRequest.OrderItemRequest itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId().intValue())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getRetailPrice());
            orderItem.setOrder(order);

            order.addOrderItem(orderItem);
            totalAmount = totalAmount.add(product.getRetailPrice().multiply(new BigDecimal(itemRequest.getQuantity())));
        }

        // Calculate totals
        order.setTotalAmount(totalAmount);
        order.setTaxAmount(calculateTax(totalAmount));
        order.setShippingAmount(calculateShipping(totalAmount));
        order.setDiscountAmount(BigDecimal.ZERO); // No discount for now

        Order savedOrder = orderRepository.save(order);

        // Send confirmation email
        emailService.sendOrderConfirmationEmail(user.getEmail(), savedOrder);

        return savedOrder;
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Page<Order> findByUser(User user, Pageable pageable) {
        return orderRepository.findByUser(user, pageable);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> findByPaymentStatus(PaymentStatus paymentStatus) {
        return orderRepository.findByPaymentStatus(paymentStatus);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public Order processPayment(Long orderId, String paymentIntentId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Process payment with Stripe
        boolean paymentSuccess = paymentService.processPayment(order, paymentIntentId);
        
        if (paymentSuccess) {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
            order.setStripePaymentIntentId(paymentIntentId);
            order.setStatus(OrderStatus.CONFIRMED);
            
            // Send payment confirmation email
            emailService.sendPaymentConfirmationEmail(order.getUser().getEmail(), order);
        } else {
            order.setPaymentStatus(PaymentStatus.FAILED);
        }

        return orderRepository.save(order);
    }

    public List<Order> findOrdersCreatedSince(LocalDateTime since) {
        return orderRepository.findOrdersCreatedSince(since);
    }

    public List<Order> findOrdersWithAmountGreaterThan(BigDecimal minAmount) {
        return orderRepository.findOrdersWithAmountGreaterThan(minAmount);
    }

    public Long countOrdersByStatus(OrderStatus status) {
        return orderRepository.countOrdersByStatus(status);
    }

    public BigDecimal sumOrderAmountsByStatusSince(OrderStatus status, LocalDateTime since) {
        return orderRepository.sumOrderAmountsByStatusSince(status, since);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private BigDecimal calculateTax(BigDecimal amount) {
        // Simple tax calculation (8.5%)
        return amount.multiply(new BigDecimal("0.085"));
    }

    private BigDecimal calculateShipping(BigDecimal amount) {
        // Free shipping for orders over $50, otherwise $5.99
        if (amount.compareTo(new BigDecimal("50.00")) >= 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal("5.99");
    }
} 
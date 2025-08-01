package com.ecommerce.controller;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.PaymentStatus;
import com.ecommerce.entity.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        // For now, use a default user ID. In a real app, this would come from JWT token
        Long userId = 1L; // This should be extracted from authentication
        
        try {
            Order order = orderService.createOrder(userId, orderRequest);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create order: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<?> getOrderByNumber(@PathVariable String orderNumber) {
        return orderService.findByOrderNumber(orderNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserOrders(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        User user = userService.findById(userId)
                .orElse(null);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.findByUser(user, pageable);
        
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<Order> orders = orderService.findByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/payment-status/{paymentStatus}")
    public ResponseEntity<?> getOrdersByPaymentStatus(@PathVariable PaymentStatus paymentStatus) {
        List<Order> orders = orderService.findByPaymentStatus(paymentStatus);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update order status: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<?> processPayment(
            @PathVariable Long id,
            @RequestParam String paymentIntentId) {
        try {
            Order order = orderService.processPayment(id, paymentIntentId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process payment: " + e.getMessage());
        }
    }

    @GetMapping("/analytics/summary")
    public ResponseEntity<?> getOrderAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        
        // Get counts by status
        analytics.put("pendingOrders", orderService.countOrdersByStatus(OrderStatus.PENDING));
        analytics.put("confirmedOrders", orderService.countOrdersByStatus(OrderStatus.CONFIRMED));
        analytics.put("shippedOrders", orderService.countOrdersByStatus(OrderStatus.SHIPPED));
        analytics.put("deliveredOrders", orderService.countOrdersByStatus(OrderStatus.DELIVERED));
        analytics.put("cancelledOrders", orderService.countOrdersByStatus(OrderStatus.CANCELLED));
        
        // Get revenue for last 30 days
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        BigDecimal recentRevenue = orderService.sumOrderAmountsByStatusSince(OrderStatus.CONFIRMED, thirtyDaysAgo);
        analytics.put("recentRevenue", recentRevenue);
        
        // Get recent orders
        List<Order> recentOrders = orderService.findOrdersCreatedSince(thirtyDaysAgo);
        analytics.put("recentOrders", recentOrders.size());
        
        return ResponseEntity.ok(analytics);
    }

    @GetMapping("/analytics/revenue")
    public ResponseEntity<?> getRevenueAnalytics(
            @RequestParam(defaultValue = "30") int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        BigDecimal revenue = orderService.sumOrderAmountsByStatusSince(OrderStatus.CONFIRMED, since);
        
        Map<String, Object> response = new HashMap<>();
        response.put("period", days + " days");
        response.put("revenue", revenue);
        response.put("since", since);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/high-value")
    public ResponseEntity<?> getHighValueOrders(
            @RequestParam(defaultValue = "100.00") BigDecimal minAmount) {
        List<Order> orders = orderService.findOrdersWithAmountGreaterThan(minAmount);
        return ResponseEntity.ok(orders);
    }
} 
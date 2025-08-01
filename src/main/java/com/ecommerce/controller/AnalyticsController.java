package com.ecommerce.controller;

import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardAnalytics() {
        Map<String, Object> dashboard = new HashMap<>();
        
        // User analytics
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        dashboard.put("totalUsers", userService.countUsersRegisteredSince(LocalDateTime.MIN));
        dashboard.put("newUsersThisMonth", userService.countUsersRegisteredSince(thirtyDaysAgo));
        dashboard.put("activeUsers", userService.findAllActiveUsers().size());
        
        // Order analytics
        dashboard.put("totalOrders", orderService.countOrdersByStatus(null)); // All orders
        dashboard.put("pendingOrders", orderService.countOrdersByStatus(com.ecommerce.entity.OrderStatus.PENDING));
        dashboard.put("confirmedOrders", orderService.countOrdersByStatus(com.ecommerce.entity.OrderStatus.CONFIRMED));
        
        // Revenue analytics
        BigDecimal recentRevenue = orderService.sumOrderAmountsByStatusSince(
                com.ecommerce.entity.OrderStatus.CONFIRMED, thirtyDaysAgo);
        dashboard.put("recentRevenue", recentRevenue);
        
        // Product analytics
        dashboard.put("totalProducts", productService.getTotalProductCount());
        dashboard.put("topCategories", productService.getTopCategories(5));
        
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/revenue/trend")
    public ResponseEntity<?> getRevenueTrend(
            @RequestParam(defaultValue = "7") int days) {
        Map<String, Object> trend = new HashMap<>();
        
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        BigDecimal revenue = orderService.sumOrderAmountsByStatusSince(
                com.ecommerce.entity.OrderStatus.CONFIRMED, since);
        
        trend.put("period", days + " days");
        trend.put("revenue", revenue);
        trend.put("averageDailyRevenue", revenue.divide(new BigDecimal(days), 2, BigDecimal.ROUND_HALF_UP));
        trend.put("since", since);
        
        return ResponseEntity.ok(trend);
    }

    @GetMapping("/users/activity")
    public ResponseEntity<?> getUserActivity(
            @RequestParam(defaultValue = "7") int days) {
        Map<String, Object> activity = new HashMap<>();
        
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        activity.put("newRegistrations", userService.countUsersRegisteredSince(since));
        activity.put("activeUsers", userService.findUsersLoggedInSince(since).size());
        activity.put("period", days + " days");
        
        return ResponseEntity.ok(activity);
    }

    @GetMapping("/orders/performance")
    public ResponseEntity<?> getOrderPerformance() {
        Map<String, Object> performance = new HashMap<>();
        
        // Order status distribution
        performance.put("pending", orderService.countOrdersByStatus(com.ecommerce.entity.OrderStatus.PENDING));
        performance.put("confirmed", orderService.countOrdersByStatus(com.ecommerce.entity.OrderStatus.CONFIRMED));
        performance.put("shipped", orderService.countOrdersByStatus(com.ecommerce.entity.OrderStatus.SHIPPED));
        performance.put("delivered", orderService.countOrdersByStatus(com.ecommerce.entity.OrderStatus.DELIVERED));
        performance.put("cancelled", orderService.countOrdersByStatus(com.ecommerce.entity.OrderStatus.CANCELLED));
        
        // High value orders
        List<com.ecommerce.entity.Order> highValueOrders = orderService.findOrdersWithAmountGreaterThan(new BigDecimal("100.00"));
        performance.put("highValueOrders", highValueOrders.size());
        
        return ResponseEntity.ok(performance);
    }

    @GetMapping("/products/popular")
    public ResponseEntity<?> getPopularProducts(
            @RequestParam(defaultValue = "10") int limit) {
        // This would typically query order items to find most ordered products
        // For now, return a placeholder
        Map<String, Object> popular = new HashMap<>();
        popular.put("message", "Popular products analytics endpoint");
        popular.put("limit", limit);
        
        return ResponseEntity.ok(popular);
    }

    @GetMapping("/sales/geographic")
    public ResponseEntity<?> getGeographicSales() {
        // This would analyze orders by shipping address
        // For now, return a placeholder
        Map<String, Object> geographic = new HashMap<>();
        geographic.put("message", "Geographic sales analytics endpoint");
        
        return ResponseEntity.ok(geographic);
    }
} 
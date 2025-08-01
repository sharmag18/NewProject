package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class EcommerceApplication {
    
    public static void main(String[] args) {
        System.out.println("🚀 Starting E-commerce REST API...");
        System.out.println("📋 Milestone 6: Complete E-commerce Platform");
        System.out.println("=============================================");
        
        SpringApplication.run(EcommerceApplication.class, args);
        
        System.out.println("✅ E-commerce REST API started successfully!");
        System.out.println("🌐 API Base URL: http://localhost:8080/api");
        System.out.println("📚 Available endpoints:");
        System.out.println("");
        System.out.println("🔐 Authentication & Authorization:");
        System.out.println("   POST /api/auth/login - User login");
        System.out.println("   POST /api/auth/register - User registration");
        System.out.println("   GET /api/auth/profile - Get user profile");
        System.out.println("");
        System.out.println("🛍️ Product Management:");
        System.out.println("   GET /api/products - Get all products (with pagination)");
        System.out.println("   GET /api/products/{id} - Get product by ID");
        System.out.println("   GET /api/products/department/{department} - Get products by department");
        System.out.println("   GET /api/products/brand/{brand} - Get products by brand");
        System.out.println("   GET /api/products/search?q={keyword} - Search products");
        System.out.println("");
        System.out.println("📦 Order Management:");
        System.out.println("   POST /api/orders - Create new order");
        System.out.println("   GET /api/orders/{id} - Get order by ID");
        System.out.println("   GET /api/orders/number/{orderNumber} - Get order by number");
        System.out.println("   GET /api/orders/user/{userId} - Get user orders");
        System.out.println("   PUT /api/orders/{id}/status - Update order status");
        System.out.println("   POST /api/orders/{id}/payment - Process payment");
        System.out.println("");
        System.out.println("📊 Analytics & Dashboard:");
        System.out.println("   GET /api/analytics/dashboard - Get dashboard analytics");
        System.out.println("   GET /api/analytics/revenue/trend - Get revenue trends");
        System.out.println("   GET /api/analytics/users/activity - Get user activity");
        System.out.println("   GET /api/analytics/orders/performance - Get order performance");
        System.out.println("");
        System.out.println("🔧 System Endpoints:");
        System.out.println("   GET /api/departments - Get all departments");
        System.out.println("   GET /api/actuator/** - Spring Boot Actuator");
        System.out.println("   GET /api/health/** - Health check endpoints");
        System.out.println("");
        System.out.println("🔧 Test the API using:");
        System.out.println("   curl http://localhost:8080/api/products");
        System.out.println("   curl -X POST http://localhost:8080/api/auth/login -H 'Content-Type: application/json' -d '{\"username\":\"admin\",\"password\":\"admin123\"}'");
        System.out.println("   curl http://localhost:8080/api/analytics/dashboard");
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
} 
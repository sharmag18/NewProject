package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.PaymentStatus;
import com.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Optional<Order> findByOrderNumber(String orderNumber);
    
    List<Order> findByUser(User user);
    
    Page<Order> findByUser(User user, Pageable pageable);
    
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findByPaymentStatus(PaymentStatus paymentStatus);
    
    List<Order> findByUserAndStatus(User user, OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.createdAt >= :since")
    List<Order> findOrdersCreatedSince(@Param("since") LocalDateTime since);
    
    @Query("SELECT o FROM Order o WHERE o.totalAmount >= :minAmount")
    List<Order> findOrdersWithAmountGreaterThan(@Param("minAmount") BigDecimal minAmount);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countOrdersByStatus(@Param("status") OrderStatus status);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = :status AND o.createdAt >= :since")
    BigDecimal sumOrderAmountsByStatusSince(@Param("status") OrderStatus status, @Param("since") LocalDateTime since);
    
    @Query("SELECT o FROM Order o WHERE o.stripePaymentIntentId = :paymentIntentId")
    Optional<Order> findByStripePaymentIntentId(@Param("paymentIntentId") String paymentIntentId);
} 
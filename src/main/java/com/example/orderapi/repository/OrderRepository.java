package com.example.orderapi.repository;

import com.example.orderapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Find order by orderId
    Optional<Order> findByOrderId(Integer orderId);
    
    // Find orders by userId
    List<Order> findByUserId(Integer userId);
    
    // Find orders by status
    List<Order> findByStatus(String status);
    
    // Find orders by userId and status
    List<Order> findByUserIdAndStatus(Integer userId, String status);
    
    // Custom query to find orders by user ID ordered by creation date
    @Query("SELECT o FROM Order o WHERE o.userId = :userId ORDER BY o.createdAt DESC")
    List<Order> findByUserIdOrderByCreatedAtDesc(@Param("userId") Integer userId);
    
    // Check if order exists by orderId
    boolean existsByOrderId(Integer orderId);
}
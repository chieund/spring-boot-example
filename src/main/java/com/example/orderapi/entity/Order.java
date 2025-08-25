package com.example.orderapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_id")
    @NotNull(message = "Order ID cannot be null")
    private Integer orderId;
    
    @Column(name = "user_id")
    @NotNull(message = "User ID cannot be null")
    private Integer userId;
    
    @Column(name = "total", precision = 10, scale = 2)
    @NotNull(message = "Total cannot be null")
    @Positive(message = "Total must be positive")
    private BigDecimal total;
    
    @Column(name = "status", length = 50)
    private String status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructors
    public Order() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Order(Integer orderId, Integer userId, BigDecimal total, String status) {
        this();
        this.orderId = orderId;
        this.userId = userId;
        this.total = total;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", total=" + total +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
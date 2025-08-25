package com.example.orderapi.service;

import com.example.orderapi.entity.Order;
import com.example.orderapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    // Create a new order
    public Order createOrder(Order order) {
        // Check if order with same orderId already exists
        if (order.getOrderId() != null && orderRepository.existsByOrderId(order.getOrderId())) {
            throw new RuntimeException("Order with ID " + order.getOrderId() + " already exists");
        }
        return orderRepository.save(order);
    }
    
    // Get all orders
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    // Get order by ID
    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    // Get order by orderId
    @Transactional(readOnly = true)
    public Optional<Order> getOrderByOrderId(Integer orderId) {
        return orderRepository.findByOrderId(orderId);
    }
    
    // Get orders by userId
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    // Get orders by status
    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
    
    // Get orders by userId and status
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserIdAndStatus(Integer userId, String status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }
    
    // Update order
    public Order updateOrder(Long id, Order orderDetails) {
        return orderRepository.findById(id)
                .map(order -> {
                    if (orderDetails.getOrderId() != null) {
                        // Check if new orderId conflicts with existing orders (excluding current one)
                        Optional<Order> existingOrder = orderRepository.findByOrderId(orderDetails.getOrderId());
                        if (existingOrder.isPresent() && !existingOrder.get().getId().equals(id)) {
                            throw new RuntimeException("Order with ID " + orderDetails.getOrderId() + " already exists");
                        }
                        order.setOrderId(orderDetails.getOrderId());
                    }
                    if (orderDetails.getUserId() != null) {
                        order.setUserId(orderDetails.getUserId());
                    }
                    if (orderDetails.getTotal() != null) {
                        order.setTotal(orderDetails.getTotal());
                    }
                    if (orderDetails.getStatus() != null) {
                        order.setStatus(orderDetails.getStatus());
                    }
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
    
    // Delete order
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
    
    // Check if order exists
    @Transactional(readOnly = true)
    public boolean orderExists(Long id) {
        return orderRepository.existsById(id);
    }
    
    // Check if order exists by orderId
    @Transactional(readOnly = true)
    public boolean orderExistsByOrderId(Integer orderId) {
        return orderRepository.existsByOrderId(orderId);
    }
}
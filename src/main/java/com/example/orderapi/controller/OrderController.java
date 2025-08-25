package com.example.orderapi.controller;

import com.example.orderapi.entity.Order;
import com.example.orderapi.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    private final OrderService orderService;
    
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    // Create a new order
    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order order) {
        try {
            Order createdOrder = orderService.createOrder(order);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error creating order: " + e.getMessage()), 
                HttpStatus.BAD_REQUEST
            );
        }
    }
    
    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                new ErrorResponse("Order not found with id: " + id), 
                HttpStatus.NOT_FOUND
            );
        }
    }
    
    // Get order by orderId
    @GetMapping("/order-id/{orderId}")
    public ResponseEntity<?> getOrderByOrderId(@PathVariable Integer orderId) {
        Optional<Order> order = orderService.getOrderByOrderId(orderId);
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                new ErrorResponse("Order not found with order ID: " + orderId), 
                HttpStatus.NOT_FOUND
            );
        }
    }
    
    // Get orders by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Integer userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    // Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    // Get orders by userId and status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByUserIdAndStatus(
            @PathVariable Integer userId, 
            @PathVariable String status) {
        List<Order> orders = orderService.getOrdersByUserIdAndStatus(userId, status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    // Update order
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody Order orderDetails) {
        try {
            Order updatedOrder = orderService.updateOrder(id, orderDetails);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error updating order: " + e.getMessage()), 
                HttpStatus.BAD_REQUEST
            );
        }
    }
    
    // Partial update order
    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        try {
            Order updatedOrder = orderService.updateOrder(id, orderDetails);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error updating order: " + e.getMessage()), 
                HttpStatus.BAD_REQUEST
            );
        }
    }
    
    // Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(
                new SuccessResponse("Order deleted successfully"), 
                HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                new ErrorResponse("Error deleting order: " + e.getMessage()), 
                HttpStatus.NOT_FOUND
            );
        }
    }
    
    // Check if order exists
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> orderExists(@PathVariable Long id) {
        boolean exists = orderService.orderExists(id);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    // Response classes for consistent API responses
    public static class ErrorResponse {
        private String error;
        private long timestamp;
        
        public ErrorResponse(String error) {
            this.error = error;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
    
    public static class SuccessResponse {
        private String message;
        private long timestamp;
        
        public SuccessResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
}
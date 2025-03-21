package com.project.billingSoftware.service;

import com.project.billingSoftware.model.Order;
import com.project.billingSoftware.model.OrderStatus;
import com.project.billingSoftware.repository.OrderRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    // ✅ Save Order
    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order); // Save Order in the database
    }

    // ✅ Get All Orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ Get Order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // ✅ Delete Order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    

    // ✅ Update Order Status
    public void updateOrderStatus(Long id, OrderStatus newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(newStatus);
            orderRepository.save(order);
        }
    }

    // ✅ Get Orders by Status (e.g., Pending, Paid, Refunded)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
}

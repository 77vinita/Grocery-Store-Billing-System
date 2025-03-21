package com.project.billingSoftware.controller;

import com.project.billingSoftware.model.Order;
import com.project.billingSoftware.model.OrderStatus;
import com.project.billingSoftware.model.Product;
import com.project.billingSoftware.repository.OrderRepository;
import com.project.billingSoftware.repository.ProductRepository;
import com.project.billingSoftware.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderRepository orderRepository; // ✅ Inject OrderRepository

    @Autowired
    private ProductRepository productRepository; // ✅ Inject ProductRepository


    // ✅ Display all orders
    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders"; // Returns orders.html
    }

//    // ✅ View order details
//    @GetMapping("/{id}")
//    public String viewOrder(@PathVariable Long id, Model model) {
//        Order order = orderService.getOrderById(id);
//        
//        if (order == null) {
//            return "redirect:/orders"; // Redirect if order not found
//        }
//        
//        System.out.println("Order ID: " + order.getId());
//        System.out.println("Product ID: " + order.getProductId()); // Debugging line
//        
//        if (order.getProductId() == null) {
//            model.addAttribute("error", "Product ID is missing for this order.");
//            return "order-details"; // Show error message
//        }
//
//        Product product = productRepository.findById(order.getProductId()).orElse(null);
//        model.addAttribute("order", order);
//        model.addAttribute("product", product);
//
//        return "order-details";
//    }

    
    
    @PostMapping("/bills/save")
    public String saveBill(@RequestParam Long customerId, @RequestParam List<Long> productIds, @RequestParam List<Integer> quantities) {
        // Create a new order
        Order order = new Order();
        
        // For each product in the bill, create an order item
        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            int quantity = quantities.get(i);
            
            // Set productId and quantity in the order
            order.setProductId(productId);
            order.setQuantity(quantity);

            // Save the order (this will save an order in the database with productId and quantity)
            orderRepository.save(order);
        }
        
        return "redirect:/orders"; // Redirect to the orders page after saving the bill
    }


    // ✅ Delete an order
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    // ✅ Filter orders by status
    @GetMapping("/status/{status}")
    public String filterOrdersByStatus(@PathVariable OrderStatus status, Model model) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        model.addAttribute("orders", orders);
        return "orders"; // Returns filtered orders list
    }
    
//    @PostMapping("/refund/{id}")
//    public String refundOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        Order order = orderRepository.findById(id).orElse(null);
//        
//        if (order == null) {
//            redirectAttributes.addFlashAttribute("error", "Order not found!");
//            return "redirect:/orders";
//        }
//
//        if (order.getStatus() == OrderStatus.REFUNDED) {
//            redirectAttributes.addFlashAttribute("error", "Order is already refunded!");
//            return "redirect:/orders";
//        }
//
//        // ✅ Validate product existence before updating stock
//        if (order.getProductId() != null) {
//            Product product = productRepository.findById(order.getProductId()).orElse(null);
//            
//            if (product != null) {
//                product.setStock(product.getStock() + order.getQuantity());
//                productRepository.save(product);
//            }
//        }
//
//        // ✅ Update order status to "Refunded"
//        order.setStatus(OrderStatus.REFUNDED);
//        orderRepository.save(order);
//        
//        redirectAttributes.addFlashAttribute("success", "Order refunded successfully!");
//        return "redirect:/orders"; // ✅ Redirect with success message
//    }
    
    @GetMapping("/refund/{id}")
    public String refundOrder(@PathVariable Long id) {
        orderService.updateOrderStatus(id, OrderStatus.REFUNDED);
        return "redirect:/orders";
    }



}


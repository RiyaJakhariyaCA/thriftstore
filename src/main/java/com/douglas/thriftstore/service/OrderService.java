package com.douglas.thriftstore.service;

import com.douglas.thriftstore.model.Orders;
import com.douglas.thriftstore.model.Product;
import com.douglas.thriftstore.model.TransactionDetails;
import com.douglas.thriftstore.model.User;
import com.douglas.thriftstore.repository.OrderRepository;
import com.douglas.thriftstore.repository.TransactionRepository;
import com.paypal.api.payments.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
  
    
    @Autowired
    private TransactionRepository transactionRepository;

    // Create or Update an Order
    public Orders saveOrder(Orders order) {
        return orderRepository.save(order);
    }

    // Retrieve all Orders
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    // Retrieve a specific Order by ID
    public Optional<Orders> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Delete an Order by ID
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    
    // Method to update order status and transaction ID after PayPal payment
    public void updateOrderStatus(String paymentId, String payerId, String transactionId, String status,String userid) {
    	
    	Orders order = orderRepository.findByUserId(userid);
    	
    
    	
        // Set the order status to "COMPLETED" (or any other status as needed)
        order.setStatus(status);

        // Optionally save the transaction as a separate record
        TransactionDetails transaction = new TransactionDetails();
        transaction.setOrderId(order.getOrderId());
        transaction.setPaymentId(paymentId);
        transaction.setTransactionId(transactionId);  // Store the PayPal transaction ID
        transaction.setStatus(status);
        transactionRepository.save(transaction);
        order.setTransactionId(transaction.getId());
        orderRepository.save(order);
    }
    
    // Method to create an order
    public Orders createOrderDetails(String userid, Double amount, String currency,Long productid) {
        // Create and save order in the database
    	Orders order = new Orders();
        order.setUserId(userid);
        order.setAmount(amount);
        order.setCurrency(currency);
        order.setProductId(productid);
        order.setStatus("PENDING");  // Order is in pending status until payment is completed
        
        return orderRepository.save(order);  // Save the order in the database
    }
}

package com.douglas.thriftstore.service;

import com.douglas.thriftstore.model.Orders;
import com.douglas.thriftstore.model.Product;
import com.douglas.thriftstore.model.TransactionDetails;
import com.douglas.thriftstore.model.User;
import com.douglas.thriftstore.repository.OrderRepository;
import com.douglas.thriftstore.repository.ProductRepository;
import com.douglas.thriftstore.repository.TransactionRepository;
import com.douglas.thriftstore.repository.UserRepository;
import com.douglas.thriftstore.utils.NumberUtils;
import com.douglas.thriftstore.utils.StringValidation;
import com.paypal.api.payments.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    
    @Autowired
    private ProductRepository productRepository;
    

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
    
    // Retrieve a specific Order by ID
    public List<Orders> getOrderByBuyerId(String userId) {
        return orderRepository.findByUserId(StringValidation.removeWhiteSpaces(userId));
    }
    
    // Retrieve a specific Order by ID
    public List<Orders> getOrderBySellerId(String userId) {
        return orderRepository.findBySellerId(StringValidation.removeWhiteSpaces(userId));
    }


    // Delete an Order by ID
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    
//    // Method to update order status and transaction ID after PayPal payment
//    public void updateOrderStatus(String paymentId, String payerId, String transactionId, String status,String userid) {
//    	
//    	Orders order = orderRepository.findByUserId(userid);
//    	
//    
//    	
//        // Set the order status to "COMPLETED" (or any other status as needed)
//        order.setStatus(status);
//
//        // Optionally save the transaction as a separate record
//        TransactionDetails transaction = new TransactionDetails();
//        transaction.setOrderId(order.getOrderId());
//        transaction.setPaymentId(paymentId);
//        transaction.setTransactionId(transactionId);  // Store the PayPal transaction ID
//        transaction.setStatus(status);
//        transactionRepository.save(transaction);
//        order.setTransactionId(transaction.getId());
//        orderRepository.save(order);
//    }
    
    // Method to create an order
    public Orders createOrderDetails(String userid, Double amount,Long productid) {
    	
    	Optional<Product> product = productRepository.findById(productid);
        // Create and save order in the database
    	Orders order = new Orders();
        order.setUserId(userid);
        order.setAmount(amount);
        order.setProductId(productid);
        order.setSellerId(product.get().getSellerId());
        order.setStatus("SUCCESSFUL");  
        order.setCurrency("CAD");
        order.setModeofPay("CREDIT CARD");
        order.setTransactionId(NumberUtils.getStreamOfRandomInts());
        
        return orderRepository.save(order);  // Save the order in the database
    }
}

package com.douglas.thriftstore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.thriftstore.model.Orders;
import com.douglas.thriftstore.service.OrderService;

import co.douglas.thriftstore.dto.CreateOrderRequestDTO;


@CrossOrigin()
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderService orderservice;
	
	
	@PostMapping("/createorder")
    public ResponseEntity<Object> createOrder(@RequestBody CreateOrderRequestDTO order) {
        try {
        	
            return ResponseEntity.ok(orderservice.createOrderDetails(order.buyerId, order.productId));
        }catch(Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment: " + e.getMessage());
        }
    }
	
	 // Get All Orders
    @GetMapping("/all")
    public ResponseEntity<Object> getAllorders() {
        try {
            List<Orders> orders = orderservice.getAllOrders();
            if (orders == null || orders.isEmpty()) {
                return ResponseEntity.status(404).body("No order found.");
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while fetching orders: " + e.getMessage());
        }
    }
    
    // Get Orders by buyer ID
    @GetMapping("/buyer/{userid}")
    public ResponseEntity<Object> getUserOrderHistory(@PathVariable String userid) {
        try {
            List<Orders> orders = orderservice.getOrderByBuyerId(userid);
            if (orders == null) {
                return ResponseEntity.status(404).body("No order found. ");
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while fetching order: " + e.getMessage());
        }
    }
    
 // Get Get Orders by seller ID
    @GetMapping("/seller/{userid}")
    public ResponseEntity<Object> getSellerOrderHistory(@PathVariable String userid) {
        try {
            List<Orders> orders = orderservice.getOrderBySellerId(userid);
            if (orders == null) {
                return ResponseEntity.status(404).body("No order found. ");
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while fetching order: " + e.getMessage());
		}
	}

}

package com.douglas.thriftstore.controller;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douglas.thriftstore.service.OrderService;
import com.douglas.thriftstore.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    @Autowired
    private PaymentService payPalService;
   

    // API to create payment and redirect user to PayPal for approval
    @PostMapping("/createorder")
    public ResponseEntity<String> createOrder(@RequestParam("userid") String userid, @RequestParam("amount") Double amount,@RequestParam("productid") Long productid) {
        try {
            // Prepare the PayPal payment and get approval URL
            String cancelUrl = "http://localhost:8080/api/paypal/cancel";
            String successUrl = "http://localhost:8080/api/paypal/confirm";
            
            Payment payment = payPalService.createPayment(userid,amount, "CAD", cancelUrl, successUrl,productid);
            for (Links link : payment.getLinks()) {
                if ("approval_url".equals(link.getRel())) {
                   System.out.println(payment.toString());
                    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(link.getHref())).build();
                }
            }
            return ResponseEntity.badRequest().body("Error creating payment");
        }catch(Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment: " + e.getMessage());
        }
        
    }


    // API to confirm payment after PayPal redirects back
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("userid") String userid) {
        try {
            // Execute the PayPal payment
            Payment executedPayment = payPalService.executePayment(paymentId, payerId,userid);

            return ResponseEntity.ok("Payment successful. Transaction ID: " + executedPayment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId());
        } catch (PayPalRESTException e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error executing payment: " + e.getMessage());
        }
    }

    // API to handle payment cancellation
    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPayment() {
        return ResponseEntity.ok("Payment was cancelled by the user.");
    }
}
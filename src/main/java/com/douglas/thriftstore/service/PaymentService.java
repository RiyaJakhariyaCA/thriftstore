package com.douglas.thriftstore.service;

import com.douglas.thriftstore.model.Orders;
import com.douglas.thriftstore.model.TransactionDetails;
import com.douglas.thriftstore.model.User;
import com.douglas.thriftstore.repository.OrderRepository;
import com.douglas.thriftstore.repository.TransactionRepository;
import com.douglas.thriftstore.repository.UserRepository;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.api.payments.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    OrderRepository orderRepository;
    

    // PayPal configuration
    private static final String CLIENT_ID = "ARpeSMSAj7HHlH_8hBh4nNFaALJ5xcHXfPryLxYMwiw-ZdQa5otNi22dytG6M-FoxJRuLKTCvOIm0j-L";
    private static final String CLIENT_SECRET = "EGPc68oHMamNgrqa-1wWX5nIJxJ9kARv-1szqtVZbIPmCC9hxHELAWKzQv0zijtG3Qm5fr1UoqEv_Vu2";
    private static final String MODE = "sandbox"; // Change to "live" for production
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";


    private APIContext apiContext;

    public PaymentService() {
        // Initialize PayPal API context
        apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
    }

    public Payment createPayment(String Userid,Double totalAmount, String currency, String cancelUrl, String successUrl,long productid) {
    	
    	//Get the user Details
    	User user = userRepository.findByUserid(Userid);
    	
    	Orders order = new Orders();
    	order.setUserId(Userid);
    	order.setAmount(totalAmount);
    	order.setCurrency(currency);
    	order.setStatus("PENDING");
    	order.setProductId(productid);
    	
    	Amount amount = new Amount();
        amount.setTotal(totalAmount.toString());
        amount.setCurrency(currency);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Payment for order");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setPayerId("dummy_payer_id")
                 .setFirstName(user.getFirst_name())
                 .setLastName(user.getLast_name())
                 .setEmail(user.getEmail());
        
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        payer.setPayerInfo(payerInfo);
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        
        try {
        	
        	orderService.createOrderDetails(Userid,totalAmount,currency,productid);
            Payment createdPayment = payment.create(apiContext);
            return createdPayment;
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Payment executePayment(String paymentId, String payerId,String userid) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        
        
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        
        //Payment executedPayment = payment.execute(apiContext, paymentExecution);
       // executedPayment.setState("approved");
        // Get transaction ID from executed payment
       // String transactionId = executedPayment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId();

        // Update order and transaction with the transactionId
        orderService.updateOrderStatus(paymentId, payerId, "123", "COMPLETED",userid);
        
        return payment;
    }
    
    public LocalDateTime stringToLocalDateTime(String s){
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS.substring(0, s.length())));
    }
}

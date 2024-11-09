package com.douglas.thriftstore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_details")
public class TransactionDetails {

   
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private String status;
    
    @Column(name = "paymentId")
    private String paymentId;

   
    public Long getId() {
        return id;
    }

    public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
   	public String toString() {
   		return "TransactionDetails [id=" + id + ", orderId=" + orderId + ", transactionDate=" + transactionDate
   				+ ", transactionId=" + transactionId + ", amount=" + amount + ", status=" + status + "]";
   	}

}

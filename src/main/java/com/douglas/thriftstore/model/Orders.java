package com.douglas.thriftstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "\"orders\"")
public class Orders {

 
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    
	@Column(name = "userId", unique = true) 
    private String userId;
	
	@Column(name = "productId") 
    private Long productId;
    
    @Column(name="amount")
	private double amount;
    
    @Column(name="status")
	private String status;
    
    @Column(name="currency")
	private String currency;

    @Column(name="transactionId")
    private Long transactionId;
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	

    public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	// Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public Orders() {
		super();
	}

	public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	   public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}


  
    
}

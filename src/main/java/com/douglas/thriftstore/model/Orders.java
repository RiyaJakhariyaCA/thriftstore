package com.douglas.thriftstore.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "\"orders\"")
public class Orders {

 
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

	@Column(name = "userId") 
    private String userId;
	
	@Column(name = "productId") 
    private Long productId;
	
	@Column(name = "sellerId") 
    private String sellerId;
    
    @Column(name="amount")
	private double amount;
    
    @Column(name="status")
	private String status;
    
    @Column(name="currency")
	private String currency;

    @Column(name="transactionId")
    private int transactionId;
    
    @Column(name="modeofPay")
    private String modeofPay;
    
    @Column(name="modifiedAt")
    @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime modifiedAt;
	 
	@Column(name="createdAt")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;
		
	
	    @PrePersist
	    public void prePersist() {
	        this.createdAt = LocalDateTime.now(); 
	        this.modifiedAt = LocalDateTime.now(); 
	        
	    }
	    
	    // This method is called before updating the entity
	    @PreUpdate
	    public void preUpdate() {
	        this.modifiedAt = LocalDateTime.now(); 
	    }

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		
		

		public String getSellerId() {
			return sellerId;
		}

		public void setSellerId(String sellerId) {
			this.sellerId = sellerId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
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

		public int getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(int transactionId) {
			this.transactionId = transactionId;
		}

		public String getModeofPay() {
			return modeofPay;
		}

		public void setModeofPay(String modeofPay) {
			this.modeofPay = modeofPay;
		}

		public LocalDateTime getModifiedAt() {
			return modifiedAt;
		}

		public void setModifiedAt(LocalDateTime modifiedAt) {
			this.modifiedAt = modifiedAt;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public Orders() {
			super();
		}

		@Override
		public String toString() {
			return "Orders [orderId=" + orderId + ", userId=" + userId + ", productId=" + productId + ", amount="
					+ amount + ", status=" + status + ", currency=" + currency + ", transactionId=" + transactionId
					+ ", modeofPay=" + modeofPay + ", modifiedAt=" + modifiedAt + ", createdAt=" + createdAt + "]";
		}

	    
    
}

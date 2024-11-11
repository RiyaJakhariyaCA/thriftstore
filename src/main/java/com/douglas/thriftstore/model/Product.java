package com.douglas.thriftstore.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name="\"product\"")
public class Product 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private double price;
	
	@Column(name="description")
	private String description;
	
	@Column(name="filePath1")
	private String filePath1;
	
	@Column(name="filePath2")
	private String filePath2;
	
	@Column(name="filePath3")
	private String filePath3;

	@Column(name="condition")
	private int condition;
	
	@Column(name="startDate")
	private LocalDate startDate;

	@Column(name="discount")
	private long discount;
	
	@Column(name="sellerId")
	private String sellerId;
	
	@Column(name="available")
	private boolean available;
	
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


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getDiscount() {
		return discount;
	}


	public void setDiscount(long discount) {
		this.discount = discount;
	}

	public Product() {
		super();
	}


	public String getFilePath1() {
		return filePath1;
	}


	public void setFilePath1(String filePath1) {
		this.filePath1 = filePath1;
	}


	public String getFilePath2() {
		return filePath2;
	}


	public void setFilePath2(String filePath2) {
		this.filePath2 = filePath2;
	}


	public String getFilePath3() {
		return filePath3;
	}


	public void setFilePath3(String filePath3) {
		this.filePath3 = filePath3;
	}


	public int getCondition() {
		return condition;
	}


	public void setCondition(int condition) {
		this.condition = condition;
	}


	


	public String getSellerId() {
		return sellerId;
	}


	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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




	public boolean isAvailable() {
		return available;
	}


	public void setAvailable(boolean available) {
		this.available = available;
	}


	public Product(String name, double price, String description,
			int condition, long discount, String sellerId, boolean available) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.sellerId = sellerId;
		this.condition = condition;
		this.discount = discount;
		this.sellerId = sellerId;
		this.available = available;
	}

}

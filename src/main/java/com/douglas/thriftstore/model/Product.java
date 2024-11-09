package com.douglas.thriftstore.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

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
	
	@Column(name="filePath")
	private String filePath;
	
	@Column(name="startDate")
	private LocalDate startDate;
	
	@Column(name="discount")
	private long discount;
	


	@Column(name="modifiedDate")
	private LocalDate modifiedDate;
	
	// This method is called before persisting (creating) the entity
    @PrePersist
    public void prePersist() {
        this.startDate = LocalDate.now(); 
        this.modifiedDate = LocalDate.now(); 
        
    }
  
    
    // This method is called before updating the entity
    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDate.now(); 
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

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
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


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	

	public Product(String name, double price, String description) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		
	}


	public Product() {
		super();
	}
	
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price +  ", filePath=" + filePath
				+ "]";
	}

}

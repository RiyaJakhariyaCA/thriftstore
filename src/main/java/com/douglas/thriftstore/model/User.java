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
@Table(name="\"users\"")
public class User 
{
	
	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getUserid() {
		return userid;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="first_name")
	private String first_name;
	
	@Column(name="last_name")
	private String last_name;
	
	@Column(name="userid")
	private String userid;
	
	

	 @Column(name="email", unique = true)
	private String email;
	
	 @Column(name="modifiedDate")
		private LocalDate modifiedDate;
	 
	 @Column(name="startDate")
		private LocalDate startDate;
		
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

	
	@Column(name="state")
	private String state;
	
	@Column(name="action")
	private boolean action;
	
	@Column(name="password")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isAction() {
        return action;
    }

	public void setAction(boolean action) {
		this.action = action;
	}



	public User(String first_name, String last_name, String userid, String email,  String state, boolean action, String password) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.userid = userid;
		this.email = email;
	
		this.state = state;
		this.action = action;
		this.password = password;
	}


	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public User() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}

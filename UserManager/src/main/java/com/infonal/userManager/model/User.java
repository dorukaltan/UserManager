package com.infonal.userManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
 
@Document(collection = "user")
public class User {
 
	@Id
	private String id;
 
	String name;
 
	String lastname;
	
	String phonenumber;

	public User(String name, String lastname, String phonenumber) {
	
		this.name = name;
		this.lastname = lastname;
		this.phonenumber = phonenumber;
	}
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id + "_" + this.name;
	}
 
	//getter, setter, toString, Constructors
 
}
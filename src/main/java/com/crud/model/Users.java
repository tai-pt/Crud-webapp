package com.crud.model;

public class Users {

	private int id;
	private String name;
	private String email;
	private String country;
	private String name2;
	private String email2;
	private String country2;

	

	public Users(int id, String name, String email, String country) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.country = country;
	}

	public Users(String name2, String email2, String country2) {
		super ();
		this.name2=name2;
		this.email2=email2;
		this.country2=country2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}

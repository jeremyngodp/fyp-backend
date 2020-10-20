package com.example.demo.persistences;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="fname")
	private String fname;

	@Column(name="lname")
	private String lname;

	@Column(name="email")
	private String email;

	@Column(name="is_staff")
	private boolean is_staff;

	public User() {}

	public User (String fname, String lname, String email, boolean is_staff){
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.is_staff = is_staff;
	}

	public boolean isIs_staff() {
		return is_staff;
	}

	public void setIs_staff(boolean is_staff) {
		this.is_staff = is_staff;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

}

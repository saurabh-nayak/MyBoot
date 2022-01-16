package com.saurabh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;



@Entity
@Table(name = "user", schema = "spring")
public class User {
	@Id
	private int id;
	@Column(name="userName")
	private String userName;
	@Column(name="pass")
	private String pass;
	@Column(name="roles")
	@DefaultValue(value="ROLE_USER")
	private String roles="ROLE_USER";
	@Column(name="status")
	@DefaultValue(value="2")
	private int status=2;
	
public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", roles=" + roles + "]";
	}
       
}

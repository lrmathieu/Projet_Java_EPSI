package com.capgemini.project.java.jfx.biz;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@NamedQuery(name="Bank.findAll", query="SELECT b FROM Bank b")
public class Bank implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String bankCode;
	
	public Bank() {
		
	}

	public Bank(String name, String bankCode) {
		
		if ( name.isEmpty() ) {
			throw new IllegalArgumentException("name cannot be empty !");
		}
		if ( bankCode.isEmpty() ) {
			throw new IllegalArgumentException("bankCode cannot be empty !");
		}
		
		this.name = name;
		this.bankCode = bankCode;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getBankCode() {
		return this.bankCode;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	
	
}

package com.capgemini.project.java.jfx.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

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
	private List<Agency> agencyList;
	
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

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	//bi-directional many-to-one association to PeriodicTransaction
	@OneToMany(mappedBy="bank")
	public List<Agency> getAgency() {
		return this.agencyList;
	}
	
	public void setAgency(List<Agency> agencyList) {
		this.agencyList = agencyList;
	}

	
}

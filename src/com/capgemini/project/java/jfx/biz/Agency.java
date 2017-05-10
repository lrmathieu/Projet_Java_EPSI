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
@NamedQuery(name="Agency.findAll", query="SELECT a FROM Agency a")
public class Agency implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String counterCode;
	private int idBank;
	private int idAddress;
	private Bank bank;
	private List<Account> accountList;
<<<<<<< HEAD
	private List<Advisor> advisorList;
=======
>>>>>>> ajoutBanque
	
	public Agency() {
		
	}

	public Agency(String name, String counterCode) {
		
		if ( name.isEmpty() ) {
			throw new IllegalArgumentException("name cannot be empty !");
		}
		if ( counterCode.isEmpty() ) {
			throw new IllegalArgumentException("counterCode cannot be empty !");
		}
		
		this.name = name;
		this.counterCode = counterCode;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public String getCounterCode() {
		return this.counterCode;
	}
	
	public void setCounterCode(String counterCode) {
		this.counterCode = counterCode;
	}
	
	/*
	public int getIdBank() {
		return this.idBank;
	}
	
	public void setIdBank(int idBank) {
		this.idBank = idBank;
	}
	*/

	public int getIdAddress() {
		return this.idAddress;
	}
	

	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}
	
	//bi-directional many-to-one association to Bank
	@ManyToOne
	@JoinColumn(name="idBank")
	public Bank getBank() {
		return this.bank;
	}
	
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
<<<<<<< HEAD
=======
	public String getBankName() {
		return this.bank.getName();
	}
	
>>>>>>> ajoutBanque
	//bi-directional one-to-many association to Account
	@OneToMany(mappedBy="agency")
	public List<Account> getAccount() {
		return this.accountList;
	}

	public void setAccount(List<Account> accountList) {
		this.accountList = accountList;
<<<<<<< HEAD
	}	
	
=======
	}
	
	/*
>>>>>>> ajoutBanque
	//bi-directional one-to-many association to Advisor
	@OneToMany(mappedBy="agency")
	public List<Advisor> getAdvisor() {
		return this.advisorList;
	}

<<<<<<< HEAD
	public void setAdvisor(List<Advisor> advisorList) {
		this.advisorList = advisorList;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
=======
	public void setAdvisor(List<Avisor> advisorList) {
		this.advisorList = advisorList;
	}
	*/
>>>>>>> ajoutBanque
		
	

}

package com.capgemini.project.java.jfx.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CountryCode database table.
 * 
 */
@Entity
@NamedQuery(name="CountryCode.findAll", query="SELECT c FROM CountryCode c")
public class CountryCode implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private List<Account> accounts;

	public CountryCode() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="countryCode")
	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setCountryCode(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setCountryCode(null);

		return account;
	}

}
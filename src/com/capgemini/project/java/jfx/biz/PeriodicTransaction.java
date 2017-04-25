package com.capgemini.project.java.jfx.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the PeriodicTransaction database table.
 * 
 */
@Entity
@NamedQuery(name="PeriodicTransaction.findAll", query="SELECT p FROM PeriodicTransaction p")
public class PeriodicTransaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int dayNumber;
	private Date endDateTransaction;
	private int idCategory;
	private int idFrequency;
	private int idTargetTransaction;
	private int idTransactionType;
	private Date transactionDate;
	private double transactionValue;
	private String wording;
	private Account account;

	public PeriodicTransaction() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getDayNumber() {
		return this.dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDateTransaction() {
		return this.endDateTransaction;
	}

	public void setEndDateTransaction(Date endDateTransaction) {
		this.endDateTransaction = endDateTransaction;
	}


	public int getIdCategory() {
		return this.idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}


	public int getIdFrequency() {
		return this.idFrequency;
	}

	public void setIdFrequency(int idFrequency) {
		this.idFrequency = idFrequency;
	}


	public int getIdTargetTransaction() {
		return this.idTargetTransaction;
	}

	public void setIdTargetTransaction(int idTargetTransaction) {
		this.idTargetTransaction = idTargetTransaction;
	}


	public int getIdTransactionType() {
		return this.idTransactionType;
	}

	public void setIdTransactionType(int idTransactionType) {
		this.idTransactionType = idTransactionType;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}


	public double getTransactionValue() {
		return this.transactionValue;
	}

	public void setTransactionValue(double transactionValue) {
		this.transactionValue = transactionValue;
	}


	public String getWording() {
		return this.wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}


	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="idAccount")
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
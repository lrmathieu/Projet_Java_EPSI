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
	private int idTransactionType;
	private Date transactionDate;
	private double transactionValue;
	private String wording;
	private Account account;
	private Frequency frequency;
	private TargetTransaction targettransaction;


	public PeriodicTransaction() {
	}

	public PeriodicTransaction(String wording, double transactionValue, int dayNumber,
			Date dateTransaction, Date endDateTransaction) {
		if(wording.isEmpty()){
			throw new IllegalArgumentException("wording cannot be empty");
		}
		if(transactionValue<=0d){
			throw new IllegalArgumentException("transactionValue must be positive");
		}
		if(dayNumber<=0 || dayNumber>=28){
			throw new IllegalArgumentException("dayNumber should be between 1 and 28");
		}
		if(dateTransaction == null){
			throw new NullPointerException("transactionDate cannot be null");
		}
		if(endDateTransaction == null){
			throw new NullPointerException("endDateTransaction cannot be null");
		}
		if(endDateTransaction.getTime()<dateTransaction.getTime()){
			throw new IllegalArgumentException("endDateTransaction should come after dateTransaction");
		}
		
		this.wording = wording;
		this.transactionValue = transactionValue;
		this.dayNumber = dayNumber;
		this.transactionDate = dateTransaction;
		this.endDateTransaction = endDateTransaction;
		
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
	
	//bi-directional many-to-one association to Frequency
	@ManyToOne
	@JoinColumn(name="idFrequency")
	public Frequency getFrequency() {
		return this.frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	
	//bi-directional many-to-one association to TargetTransaction
	@ManyToOne
	@JoinColumn(name="idTargetTransaction")
	public TargetTransaction getTargetTransaction() {
		return this.targettransaction;
	}

	public void setTargetTransaction(TargetTransaction targettransaction) {
		this.targettransaction = targettransaction;
	}

}
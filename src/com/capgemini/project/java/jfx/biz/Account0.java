package com.capgemini.project.java.jfx.biz;

import java.util.Calendar;
import java.util.Date;

public class Account0 {

	public Account0(String typeDescription, String accountNumber, Date creationDate,
			double firstTotal, int overdraft, double interestRate, double alertTreshold) {
		
		if ( typeDescription==null ) {
			throw new NullPointerException("typeDescription cannot be null !");
		}
		if ( accountNumber==null ) {
			throw new NullPointerException("accountNumber cannot be null !");
		}
		if ( creationDate==null ) {
			throw new NullPointerException("created cannot be null !");
		}
		if ( creationDate.getTime() > today().getTime() ) {
			throw new IllegalArgumentException("created cannot be in the future !");
		}
		if ( firstTotal<0.0 ) {
			throw new IllegalArgumentException("firstTotal must be strictly positive !");
		}
		if ( overdraft>0 ) {
			throw new IllegalArgumentException("overdraft cannot be strictly positive !");
		}
		if ( interestRate<0.0 ) {
			throw new IllegalArgumentException("interestRate cannot be strictly negative !");
		}
		if ( interestRate>100.0 ) {
			throw new IllegalArgumentException("interestRate cannot be greater than 100 !");
		}
		
		
		this.m_typeDescription = typeDescription;
		this.m_accountNumber = accountNumber;
		this.m_creationDate = creationDate;
		this.m_firstTotal = firstTotal;
		this.m_overdraft = overdraft;
		this.m_interestRate = interestRate;
		this.m_alertTreshold = alertTreshold;
	}
	
	public int getId() {
		return this.m_id;
	}
	
	public String getTypeDescription() {
		return this.m_typeDescription;
	}
	
	public String getAccountNumber() {
		return this.m_accountNumber;
	}
	
	public Date getCreationDate() {
		return this.m_creationDate;
	}
	
	public double getFirstTotal() {
		return this.m_firstTotal;
	}
	
	public int getOverdraft() {
		return this.m_overdraft;
	}
	
	public double getInterestRate() {
		return this.m_interestRate;
	}
	
	public double getAlertTreshold() {
		return this.m_alertTreshold;
	}
	
	public void setId(int id) {
		this.m_id = id;
	}
	
	public void setOverdraft(int overdraft) {
		this.m_overdraft = overdraft;
	}
	
	public void setInterestRate(double interestRate) {
		this.m_interestRate = interestRate;
	}
	
	public void setAlertTreshold(double alertTreshold) {
		this.m_alertTreshold = alertTreshold;
	}
	
	private Date today() {
		return Calendar.getInstance().getTime();
	}
	
	
	private int m_id;
	private String m_typeDescription;
	private String m_accountNumber;
	private Date m_creationDate;
	private double m_firstTotal;
	private int m_overdraft;
	private double m_interestRate;
	private double m_alertTreshold;
	

}

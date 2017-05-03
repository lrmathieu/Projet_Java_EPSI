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
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String accountNumber;
	private double alertTreshold;
	private Date creationDate;
	private double firstTotal;
	private int idAccountType;
	private int idAgency;
	private double interestRate;
	private int overdraft;
	private String typeDescription;
	private CountryCode countryCode;
	private List<PeriodicTransaction> periodicTransactions;
	private List<Owner> owners;
	private Agency agency;

	public Account() {
	}
	public Account(String typeDescription, String accountNumber, Date creationDate,
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
		
		
		this.typeDescription = typeDescription;
		this.accountNumber = accountNumber;
		this.creationDate = creationDate;
		this.firstTotal = firstTotal;
		this.overdraft = overdraft;
		this.interestRate = interestRate;
		this.alertTreshold = alertTreshold;
	}
	
	private Date today() {
		return Calendar.getInstance().getTime();
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public double getAlertTreshold() {
		return this.alertTreshold;
	}

	public void setAlertTreshold(double alertTreshold) {
		this.alertTreshold = alertTreshold;
	}


	@Temporal(TemporalType.DATE)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public double getFirstTotal() {
		return this.firstTotal;
	}

	public void setFirstTotal(double firstTotal) {
		this.firstTotal = firstTotal;
	}


	public int getIdAccountType() {
		return this.idAccountType;
	}

	public void setIdAccountType(int idAccountType) {
		this.idAccountType = idAccountType;
	}

	/*
	public int getIdAgency() {
		return this.idAgency;
	}

	public void setIdAgency(int idAgency) {
		this.idAgency = idAgency;
	}
	*/


	public double getInterestRate() {
		return this.interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}


	public int getOverdraft() {
		return this.overdraft;
	}

	public void setOverdraft(int overdraft) {
		this.overdraft = overdraft;
	}


	public String getTypeDescription() {
		return this.typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}


	//bi-directional many-to-one association to Countrycode
	@ManyToOne
	@JoinColumn(name="idCountryCode")
	public CountryCode getCountryCode() {
		return this.countryCode;
	}

	public void setCountrycode(CountryCode countrycode) {
		this.countryCode = countrycode;
	}


	//bi-directional many-to-one association to PeriodicTransaction
	@OneToMany(mappedBy="account")
	public List<PeriodicTransaction> getPeriodictransactions() {
		return this.periodicTransactions;
	}

	public void setPeriodictransactions(List<PeriodicTransaction> periodictransactions) {
		this.periodicTransactions = periodictransactions;
	}

	public PeriodicTransaction addPeriodictransaction(PeriodicTransaction periodictransaction) {
		getPeriodictransactions().add(periodictransaction);
		periodictransaction.setAccount(this);

		return periodictransaction;
	}

	public PeriodicTransaction removePeriodictransaction(PeriodicTransaction periodictransaction) {
		getPeriodictransactions().remove(periodictransaction);
		periodictransaction.setAccount(null);

		return periodictransaction;
	}
	
	public void setCountryCode(CountryCode countryCode) {
		// TODO Auto-generated method stub
		
	}
	
	//bi-directional many-to-one association to Agency
	@ManyToOne
	@JoinColumn(name="idAgency")
	public Agency getAgency() {
		return this.agency;
	}
	
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	
	public String getAgencyName() {
		return this.agency.getName();
	}

/*
	//bi-directional many-to-many association to Owner
	@ManyToMany
	@JoinTable
	@JoinColumn(name="id")
	public List<Owner> getOwners() {
		return this.owners;
	}

	public void setOwners(List<Owner> owners) {
		this.owners = owners;
	}
*/
}
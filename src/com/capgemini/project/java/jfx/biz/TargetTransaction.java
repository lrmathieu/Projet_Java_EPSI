package com.capgemini.project.java.jfx.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the targetTransaction database table.
 * 
 */
@Entity
@NamedQuery(name="TargetTransaction.findAll", query="SELECT t FROM TargetTransaction t")
public class TargetTransaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String iban;
	private String summary;
	private List<PeriodicTransaction> PeriodicTransactions;

	public TargetTransaction() {
	}
	
	public TargetTransaction(String summary, String iban){	
		if(summary.isEmpty()){
			throw new IllegalArgumentException("summary cannot be empty");
		}
		if(iban == null){
			throw new NullPointerException("iban cannot be null");
		}
		this.summary = summary;
		this.iban = iban;
	}
	public TargetTransaction(String summary){
		this(summary, "");
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}


	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}


	//bi-directional many-to-one association to PeriodicTransaction
	@OneToMany(mappedBy="targetTransaction")
	public List<PeriodicTransaction> getPeriodicTransactions() {
		return this.PeriodicTransactions;
	}

	public void setPeriodicTransactions(List<PeriodicTransaction> PeriodicTransactions) {
		this.PeriodicTransactions = PeriodicTransactions;
	}

	public PeriodicTransaction addPeriodicTransaction(PeriodicTransaction PeriodicTransaction) {
		getPeriodicTransactions().add(PeriodicTransaction);
		PeriodicTransaction.setTargetTransaction(this);

		return PeriodicTransaction;
	}

	public PeriodicTransaction removePeriodicTransaction(PeriodicTransaction PeriodicTransaction) {
		getPeriodicTransactions().remove(PeriodicTransaction);
		PeriodicTransaction.setTargetTransaction(null);

		return PeriodicTransaction;
	}

}
package com.capgemini.project.java.jfx.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TransactionType database table.
 * 
 */
@Entity
@NamedQuery(name="TransactionType.findAll", query="SELECT t FROM TransactionType t")
public class TransactionType implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String wording;
	private List<PeriodicTransaction> periodictransactions;

	public TransactionType() {
	}
	
	public TransactionType(String wording){	
		if(wording.isEmpty()){
			throw new IllegalArgumentException("wording cannot be empty");
		}
		this.wording = wording;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getWording() {
		return this.wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}


	//bi-directional many-to-one association to PeriodicTransaction
	@OneToMany(mappedBy="transactionType")
	public List<PeriodicTransaction> getPeriodicTransactions() {
		return this.periodictransactions;
	}

	public void setPeriodicTransactions(List<PeriodicTransaction> periodictransactions) {
		this.periodictransactions = periodictransactions;
	}

	public PeriodicTransaction addPeriodicTransaction(PeriodicTransaction periodictransaction) {
		getPeriodicTransactions().add(periodictransaction);
		periodictransaction.setTransactionType(this);

		return periodictransaction;
	}

	public PeriodicTransaction removePeriodicTransaction(PeriodicTransaction periodictransaction) {
		getPeriodicTransactions().remove(periodictransaction);
		periodictransaction.setTransactionType(null);

		return periodictransaction;
	}
	
	@Override
	public String toString() {
		return this.wording;
	}

}
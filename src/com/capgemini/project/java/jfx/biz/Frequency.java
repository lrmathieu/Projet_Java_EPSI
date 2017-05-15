package com.capgemini.project.java.jfx.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the frequency database table.
 * 
 */
@Entity
@NamedQuery(name="Frequency.findAll", query="SELECT f FROM Frequency f")
public class Frequency implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int unit;
	private List<PeriodicTransaction> PeriodicTransactions;

	public Frequency() {
	}

	public Frequency(int unit) {
		this.unit = unit;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getUnit() {
		return this.unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}


	//bi-directional many-to-one association to PeriodicTransaction
	@OneToMany(mappedBy="frequency")
	public List<PeriodicTransaction> getPeriodicTransactions() {
		return this.PeriodicTransactions;
	}

	public void setPeriodicTransactions(List<PeriodicTransaction> PeriodicTransactions) {
		this.PeriodicTransactions = PeriodicTransactions;
	}

//	public PeriodicTransaction addPeriodicTransaction(PeriodicTransaction PeriodicTransaction) {
//		getPeriodicTransactions().add(PeriodicTransaction);
//		PeriodicTransaction.setFrequency(this);
//
//		return PeriodicTransaction;
//	}
//
//	public PeriodicTransaction removePeriodicTransaction(PeriodicTransaction PeriodicTransaction) {
//		getPeriodicTransactions().remove(PeriodicTransaction);
//		PeriodicTransaction.setFrequency(null);
//
//		return PeriodicTransaction;
//	}

}
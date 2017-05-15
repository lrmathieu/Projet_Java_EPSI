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
		if(unit<=0d){
			throw new IllegalArgumentException("unit must be positive");
		}
		this.unit = unit;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		if(id<=0d){
			throw new IllegalArgumentException("The id must be positive");
		}
		this.id = id;
	}


	public int getUnit() {
		return this.unit;
	}

	public void setUnit(int unit) {
		if(unit<=0d){
			throw new IllegalArgumentException("unit must be positive");
		}
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
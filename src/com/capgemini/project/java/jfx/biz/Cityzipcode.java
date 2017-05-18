package com.capgemini.project.java.jfx.biz;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cityzipcode database table.
 * 
 */
@Entity
@NamedQuery(name="Cityzipcode.findAll", query="SELECT c FROM Cityzipcode c")
public class Cityzipcode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String city;

	private String zipCode;

	public Cityzipcode() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
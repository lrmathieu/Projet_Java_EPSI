package com.capgemini.project.java.jfx.biz;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the advisor database table.
 * 
 */
@Entity
@NamedQuery(name="Advisor.findAll", query="SELECT a FROM Advisor a")
public class Advisor implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date assignmentDate;
	private String email;
	private String firstName;
	private String name;
	private String phone;
	private Agency agency;

	public Advisor() {
	}
	
	public Advisor(Date assignmentDate, String email, String firstName, String name, String phone) {
		
		if (assignmentDate==null) {
			throw new NullPointerException("assignmentDate cannot be null !");
		}
		if (email==null) {
			throw new NullPointerException("email cannot be null !");
		}
		if (firstName==null) {
			throw new NullPointerException("firstName cannot be null !");
		}
		if (name==null) {
			throw new NullPointerException("name cannot be null !");
		}
		if (phone==null) {
			throw new NullPointerException("phone cannot be null !");
		}
		
		this.assignmentDate = assignmentDate;
		this.email = email;
		this.firstName = firstName;
		this.name = name;
		this.phone = phone;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	public Date getAssignmentDate() {
		return this.assignmentDate;
	}

	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

}
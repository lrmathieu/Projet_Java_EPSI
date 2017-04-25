package com.capgemini.project.java.jfx.biz;

public class Bank {

	public Bank(String name, String bankCode) {
		
		if ( name.isEmpty() ) {
			throw new IllegalArgumentException("name cannot be empty !");
		}
		if ( bankCode.isEmpty() ) {
			throw new IllegalArgumentException("bankCode cannot be empty !");
		}
		
		this.m_name = name;
		this.m_bankCode = bankCode;
	}
	
	public int getId() {
		return this.m_id;
	}
	
	public String getName() {
		return this.m_name;
	}
	
	public String getBankCode() {
		return this.m_bankCode;
	}
	
	public void setId(int id) {
		this.m_id = id;
	}
	
	public void setName(String name) {
		this.m_name = name;
	}
	
	public void setBankCode(String bankCode) {
		this.m_bankCode = bankCode;
	}

	private int m_id;
	private String m_name;
	private String m_bankCode;
	
}

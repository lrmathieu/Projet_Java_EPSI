package com.capgemini.project.java.jfx.biz;

public class Agency {

	public Agency(String name, String counterCode) {
		
		if ( name.isEmpty() ) {
			throw new IllegalArgumentException("name cannot be empty !");
		}
		if ( counterCode.isEmpty() ) {
			throw new IllegalArgumentException("counterCode cannot be empty !");
		}
		
		this.m_name = name;
		this.m_counterCode = counterCode;
	}
	
	public int getId() {
		return this.m_id;
	}
	
	public String getName() {
		return this.m_name;
	}
	
	public String getCounterCode() {
		return this.m_counterCode;
	}
	
	public void setId(int id) {
		this.m_id = id;
	}
	
	
	private int m_id;
	private String m_name;
	private String m_counterCode;

}

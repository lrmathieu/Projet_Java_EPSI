package com.capgemini.project.java.jfx.biz;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestBank {
	
	@Test (expected = IllegalArgumentException.class) 
	public void testBank_NameIsEmpty() { 
		new Bank(null, "12345"); 
	} 

	@Test (expected = IllegalArgumentException.class) 
	public void testAgency_BankCodeIsEmpty() { 
		new Bank("foobar", null); 
	}
	
	@Test
	public void testGetName() {
		this.tested = new Bank("foobar", "12345");
		assertEquals("foobar", this.tested.getName());
	}
	
	@Test
	public void testSetName() {
		this.tested = new Bank("foobar", "12345");
		this.tested.setName("xyzzy");
		assertEquals("xyzzy", this.tested.getName());
	}
	
	@Test
	public void testGetBankCode() {
		this.tested = new Bank("foobar", "12345");
		assertEquals("12345", this.tested.getBankCode());
	}
	
	@Test
	public void testSetBankCode() {
		this.tested = new Bank("foobar", "56789");
		this.tested.setBankCode("12345");
		assertEquals("12345", this.tested.getBankCode());
	}
	
	
	private Bank tested;
}
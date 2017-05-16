package com.capgemini.project.java.jfx.model.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.project.java.jfx.biz.TargetTransaction;

public class TestTargetTransaction {
	
	@Before
	public void setup(){
		this.tested = new TargetTransaction("epsi", "7894562RT588");
	}
	
	@Test(expected = NullPointerException.class)
	public void testTargetTrans_CreateSummaryNull() {
		InitTargetTransaction(null, "7894562RT588");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testTargetTrans_CreateSummaryEmpty() {
		InitTargetTransaction("", "7894562RT588");
	}
	@Test(expected = NullPointerException.class)
	public void testTargetTrans_CreateIbanNull() {
		InitTargetTransaction("epsi", null);
	}
	@Test
	public void testTargetTrans_CreateIbanEmpty() {
		InitTargetTransaction("epsi", "");
	}
	
	@Test
	public void testTargetTrans_GetId() {
		setup();
		assertEquals(0, this.tested.getId());
	}	
	@Test
	public void testTargetTrans_SetId() {
		setup();
		Integer testId = 17;
		this.tested.setId(testId);
		assertEquals(17, this.tested.getId());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testTargetTrans_SetNegativeId() {
		setup();
		this.tested.setId(-5);
	}
	
	@Test
	public void testTargetTrans_GetSummary() {
		setup();
		assertEquals("epsi", this.tested.getSummary());
	}
	@Test
	public void testTargetTrans_SetSummary(){
		setup();
		String testSummary = "Nobody";
	    this.tested.setSummary(testSummary);
	    assertEquals(testSummary, this.tested.getSummary());
	}
	@Test(expected = NullPointerException.class)
	public void testTargetTrans_SetSummaryNull() {
		setup();
		this.tested.setSummary(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testTargetTrans_SetSummaryEmpty() {
		setup();
		this.tested.setSummary("");
	}
	
	@Test
	public void testTargetTrans_GetIban() {
		setup();
		assertEquals("7894562RT588", this.tested.getIban());
	}
	@Test
	public void testTargetTrans_SetIban(){
		setup();
		String testIban = "FR76123456789112345678901";
	    this.tested.setIban(testIban);
	    assertEquals(testIban, this.tested.getIban());
	}
	@Test//(expected = NullPointerException.class)
	public void testTargetTrans_SetIbanNull() {
		setup();
		String testIbanNull = null;
	    this.tested.setIban(testIbanNull);
	    String result = this.tested.getIban();
	    assertEquals(testIbanNull, result);
	}
	
	@Test
	public void testTargetTrans_GetPeriodicTransactions() {
		setup();
		assertEquals(null, this.tested.getPeriodicTransactions());
	}
	
	@Test
	public void testTargetTrans_ToString(){
		setup();
		assertEquals(this.tested.getSummary(), "epsi");
	}
	
	private void InitTargetTransaction(String summary, String iban){
		this.tested = new TargetTransaction(summary, iban);
	}
	
	private TargetTransaction tested;
}

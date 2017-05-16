package com.capgemini.project.java.jfx.model.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.project.java.jfx.biz.PeriodicTransaction;
import com.capgemini.project.java.jfx.biz.TransactionType;

public class TestTransactionType {

	@Before
	public void setup(){
		this.tested = new TransactionType("cheque");
	}
	
	@Test(expected = NullPointerException.class)
	public void testTransType_CreateWordingNull() {
		InitTransactionType(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testTransType_CreateWordingEmpty() {
		InitTransactionType("");
	}	
	
	@Test
	public void testTransType_GetId() {
		setup();
		assertEquals(0, this.tested.getId());
	}	
	@Test
	public void testTransType_SetId() {
		setup();
		Integer testId = 14;
		this.tested.setId(testId);
		assertEquals(14, this.tested.getId());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testTransType_SetNegativeId() {
		setup();
		this.tested.setId(-5);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testTransType_SetIdToZero() {
		setup();
		this.tested.setId(0);
	}
	
	@Test
	public void testTransType_GetWording() {
		setup();
		assertEquals("cheque", this.tested.getWording());
	}	
	@Test
	public void testTransType_SetWording(){
		setup();
		String testWording = "cash";
	    this.tested.setWording(testWording);
	    assertEquals(testWording, this.tested.getWording());
	}
	@Test(expected = NullPointerException.class)
	public void testTransType_SetWordingNull() {
		setup();
		String testWording = null;
	    this.tested.setWording(testWording);
	    assertEquals(testWording, this.tested.getWording());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testTransType_SetWordingEmpty() {
		setup();
		String testWording = "";
	    this.tested.setWording(testWording);
	    assertEquals(testWording, this.tested.getWording());
	}
	
	@Test
	public void testTransType_GetPeriodicTransactions() {
		setup();
		assertEquals(null, this.tested.getPeriodicTransactions());
	}
	
	@Test
	public void testTransType_ToString(){
		setup();
		assertEquals(this.tested.getWording(), "cheque");
	}
/*	@Test
	public void testTargetTrans_SetPeriodicTransactions() {
		setup();
		PeriodicTransaction testPerTrans = new PeriodicTransaction();
		this.listTestPerTrans.add(testPerTrans);
		assertEquals(testPerTrans, this.tested.setPeriodicTransactions(this.listTestPerTrans));
		parse
	}*/
/*	@Test
	public void testTransType_AddPeriodicTransaction() {
		setup();
		PeriodicTransaction testPerTrans = new PeriodicTransaction();
		assertEquals(null, this.tested.addPeriodicTransaction(testPerTrans));
	}
	@Test
	public void testTransType_RemovePeriodicTransaction() {
		setup();
		PeriodicTransaction testPerTrans = new PeriodicTransaction();
		assertEquals(null, this.tested.removePeriodicTransaction(testPerTrans));
	}*/
	
	private void InitTransactionType(String wording){
		this.tested = new TransactionType(wording);
	}
	
	
	private TransactionType tested;

}

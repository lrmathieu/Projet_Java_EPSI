package com.capgemini.project.java.jfx.model.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.project.java.jfx.biz.Frequency;

public class TestFrequency {

	@Before
	public void setup(){
		this.tested = new Frequency(1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPeriodTrans_TransactionValueIsNegative() {
		InitFrequency(-3);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testPeriodTrans_TransactionValueIsZero() {
		InitFrequency(0);
	}
	
	@Test
	public void testFrequency_GetId() {
		setup();
		assertEquals(0, this.tested.getId());
	}	
	@Test
	public void testFrequency_SetId() {
		setup();
		Integer testId = 14;
		this.tested.setId(testId);
		assertEquals(14, this.tested.getId());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testFrequency_SetNegativeId() {
		setup();
		this.tested.setId(-5);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testFrequency_SetIdToZero() {
		setup();
		this.tested.setId(0);
	}
	
	@Test
	public void testFrequency_GetUnit() {
		setup();
		assertEquals(1, this.tested.getUnit());
	}	
	@Test
	public void testFrequency_SetUnit(){
		setup();
		Integer testUnit = 12;
	    this.tested.setUnit(testUnit);
	    Integer result = this.tested.getUnit();
	    assertEquals(testUnit, result);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testFrequency_SetNegativeUnit() {
		setup();
		this.tested.setUnit(-5);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testFrequency_SetUnitToZero() {
		setup();
		this.tested.setUnit(0);
	}
	
	@Test
	public void testFrequency_GetPeriodicTransactions() {
		setup();
		assertEquals(null, this.tested.getPeriodicTransactions());
	}
	
	private void InitFrequency(Integer unit){
		this.tested = new Frequency(unit);
	}
	
	private Frequency tested;
}

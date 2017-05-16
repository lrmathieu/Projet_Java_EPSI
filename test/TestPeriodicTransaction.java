package com.capgemini.project.java.jfx.model.tests;

import static org.junit.Assert.assertEquals;

//import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.project.java.jfx.biz.Account;
import com.capgemini.project.java.jfx.biz.Frequency;
import com.capgemini.project.java.jfx.biz.PeriodicTransaction;
import com.capgemini.project.java.jfx.biz.TargetTransaction;
import com.capgemini.project.java.jfx.biz.TransactionType;

public class TestPeriodicTransaction {
	
	@Before
	public void setup(){
		Calendar cal_endDateTrans = new GregorianCalendar(2017, Calendar.APRIL, 06, 12, 31, 15);
		Calendar cal_dateTrans = new GregorianCalendar(2017, Calendar.APRIL, 05, 12, 31, 15);
		
		this.m_tested = new PeriodicTransaction("epsi", 109.99, 3, 
				cal_dateTrans.getTime(), cal_endDateTrans.getTime());
	
	}
	
	@Test(expected = NullPointerException.class)
	public void testPeriodTrans_WordingIsNull() {
		InitPeriodicTransaction(null, 109.99, 3, new Date(0), new Date(1));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testPeriodTrans_WordingIsEmpty() {
		InitPeriodicTransaction("", 109.99, 3, new Date(0), new Date(1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPeriodTrans_TransactionValueIsNegative() {
		InitPeriodicTransaction("epsi", -25, 3, new Date(0), new Date(1));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testPeriodTrans_TransactionValueIsZero() {
		InitPeriodicTransaction("epsi", 0, 3, new Date(0), new Date(1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPeriodTrans_DayNumberIsZero() {
		InitPeriodicTransaction("epsi", 109.99, 0, new Date(0), new Date(1));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testPeriodTrans_DayNumberIsNegative() {
		InitPeriodicTransaction("epsi", 109.99, -5, new Date(0), new Date(1));
	}
	@Test
	public void testPeriodTrans_DayNumberIs31() {
		InitPeriodicTransaction("epsi", 109.99, 31, new Date(0), new Date(1));
		assertEquals(31, this.m_tested.getDayNumber());		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testPeriodTrans_DayNumberIsGreaterThan31() {
		InitPeriodicTransaction("epsi", 109.99, 69, new Date(0), new Date(1));
	}
	
	@Test(expected = NullPointerException.class)
	public void testPeriodTrans_TransactionDateIsNull() {
		InitPeriodicTransaction("epsi", 109.99, 3, null, new Date(0));
	}
	@Test(expected = NullPointerException.class)
	public void testPeriodTrans_EndTransactionDateIsNull() {
		InitPeriodicTransaction("epsi", 109.99, 3, new Date(0), null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testPeriodTrans_EndTransactionDateComesBeforeTransactionDate() {
		Calendar cal_endDateTrans = new GregorianCalendar(2017, Calendar.APRIL, 05, 12, 31, 15);
		Calendar cal_dateTrans = new GregorianCalendar(2017, Calendar.APRIL, 11, 12, 31, 15);

		InitPeriodicTransaction("epsi", 109.99, 3, cal_dateTrans.getTime(), cal_endDateTrans.getTime());
	} 
	@Test
	public void testPeriodTrans_EndTransactionDateEqualsTransactionDate() {
		Calendar cal_endDateTrans = new GregorianCalendar(2017, Calendar.APRIL, 05, 12, 31, 15);
		Calendar cal_dateTrans = new GregorianCalendar(2017, Calendar.APRIL, 05, 12, 31, 15);

		this.m_tested = new PeriodicTransaction("epsi", 109.99, 3, cal_dateTrans.getTime(), cal_endDateTrans.getTime());
		assertEquals(this.m_tested.getTransactionDate(), this.m_tested.getEndDateTransaction());		
	}
	
	@Test
	public void testGetId() {
		setup();
		assertEquals(0, this.m_tested.getId());
	}	
	@Test
	public void testSetId() {
		setup();
		Integer testId = 27;
		this.m_tested.setId(testId);
		assertEquals(27, this.m_tested.getId());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetNegativeId() {
		setup();
		this.m_tested.setId(-5);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSetIdToZero() {
		setup();
		this.m_tested.setId(0);
	}
	
	@Test
	public void testGetWording() {
		setup();
		assertEquals("epsi", this.m_tested.getWording());		
	}
	@Test
	public void testSetWording(){
		setup();
		String testWording = "epsi";
	    this.m_tested.setWording(testWording);
	    assertEquals(testWording, this.m_tested.getWording());
	}
	@Test(expected = NullPointerException.class)
	public void testTransType_SetWordingNull() {
		setup();
		String testWording = null;
	    this.m_tested.setWording(testWording);
	    assertEquals(testWording, this.m_tested.getWording());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testTransType_SetWordingEmpty() {
		setup();
		String testWording = "";
	    this.m_tested.setWording(testWording);
	    assertEquals(testWording, this.m_tested.getWording());	}
	
	@Test
	public void testGetTransactionValue() {
		setup();
		assertEquals(109.99, this.m_tested.getTransactionValue(), 0.01);		
	}
	@Test
	public void testSetTransactionValue(){
		setup();
		Double testTransactionValue = 12.99;
	    this.m_tested.setTransactionValue(testTransactionValue);
	    Double result = this.m_tested.getTransactionValue();
	    assertEquals(testTransactionValue, result);
	}
	
	@Test
	public void testGetTransactionDate() {
		setup();
		Calendar calDateTrans = new GregorianCalendar(2017, Calendar.APRIL, 05, 12, 31, 15);
		assertEquals(calDateTrans.getTime(), 
				this.m_tested.getTransactionDate());		
	}
	@Test
	public void testSetTransactionDate(){
		setup();
		Calendar calDateTrans = new GregorianCalendar(2017, Calendar.MAY, 03, 13, 31, 15);
	    this.m_tested.setTransactionDate(calDateTrans.getTime());
	    Date result = this.m_tested.getTransactionDate();
	    assertEquals(calDateTrans.getTime(), result);
	}
	
	@Test
	public void testGetEndDateTransaction() {
		setup();
		Calendar calEndDateTrans = new GregorianCalendar(2017, Calendar.APRIL, 06, 12, 31, 15);
		assertEquals(calEndDateTrans.getTime(), 
				this.m_tested.getEndDateTransaction());		
	}
	@Test
	public void testSetEndDateTransaction(){
		setup();
		Calendar calEndDateTrans = new GregorianCalendar(2017, Calendar.MAY, 06, 14, 31, 15);
	    this.m_tested.setEndDateTransaction(calEndDateTrans.getTime());
	    Date result = this.m_tested.getEndDateTransaction();
	    assertEquals(calEndDateTrans.getTime(), result);
	}
	
	@Test
	public void testGetDayNumber() {
		setup();
		assertEquals(3, this.m_tested.getDayNumber());		
	}
	@Test
	public void testSetDayNumber(){
		setup();
		Integer testDayNumber = 7;
	    this.m_tested.setDayNumber(testDayNumber);
	    Integer result = this.m_tested.getDayNumber();
	    assertEquals(testDayNumber, result);
	}

	
	@Test
	public void testGetAccount() {
		setup();
        Account result = this.m_tested.getAccount();
        assertEquals(result, null);	
	}
	@Test
	public void testSetAccount() {
		setup();
        Account result = new Account();
        this.m_tested.setAccount(result);
        assertEquals(this.m_tested.getAccount(), result);	
	}
	
	@Test
	public void testGetFrequency() {
		setup();
		Frequency result = this.m_tested.getFrequency();
        assertEquals(result, null);	
	}
	@Test
	public void testSetFrequency() {
		setup();
		Frequency result = new Frequency();
        this.m_tested.setFrequency(result);
        assertEquals(this.m_tested.getFrequency(), result);	
	}
	
	@Test
	public void testGetTargetTransaction() {
		setup();
        TargetTransaction result = this.m_tested.getTargetTransaction();
        assertEquals(result, null);	
	}
	@Test
	public void testSetTargetTransaction() {
		setup();
        TargetTransaction result = new TargetTransaction();
        this.m_tested.setTargetTransaction(result);
        assertEquals(this.m_tested.getTargetTransaction(), result);	
	}
	
	@Test
	public void testGetTransactionType() {
		setup();
        TransactionType result = this.m_tested.getTransactionType();
        assertEquals(result, null);	
	}
	@Test
	public void testSetTransactionType() {
		setup();
		TransactionType result = new TransactionType();
        this.m_tested.setTransactionType(result);
        assertEquals(this.m_tested.getTransactionType(), result);	
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void testPeriodTrans_SetIdIsNegative() {
//		PeriodicTransaction a = new PeriodicTransaction("epsi", 109.99, 3, new Date(0), null);
//		a.setId(-5);
//	}
	
	private void InitPeriodicTransaction(String wording, double transactionValue, int dayNumber,
			Date dateTransaction, Date endDateTransaction){
		
		this.m_tested = new PeriodicTransaction(wording, transactionValue, dayNumber, 
				dateTransaction, endDateTransaction);
	}
	
	private PeriodicTransaction m_tested;

}

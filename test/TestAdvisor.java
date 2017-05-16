package com.capgemini.project.java.jfx.model.tests;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.project.java.jfx.biz.Advisor;

public class TestAdvisor {

	@Before
	public void setup(){
		Calendar cal = new GregorianCalendar(2015, Calendar.JULY, 31);
		this.m_tested = new Advisor(cal.getTime(), "jaimemabanque@laposte.fr", "Gemma", 
				"Bhank", "063113832");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAdvisor_NameIsEmpty() {
		InitAdvisor("", "Gemma", "063113832", "jaimemabanque@laposte.fr", new Date(0));
	}
	@Test(expected = NullPointerException.class)
	public void testAdvisor_NameIsNull() {
		InitAdvisor(null, "Gemma", "063113832", "jaimemabanque@laposte.fr", new Date(0));	
	}
	@Test(expected = IllegalArgumentException.class)
	public void testAdvisor_FirstNameIsEmpty() {
		InitAdvisor("Bhank", "", "063113832", "jaimemabanque@laposte.fr", new Date(0));	
	}
	@Test(expected = NullPointerException.class)
	public void testAdvisor_FirstNameIsNull() {
		InitAdvisor("Bhank", null, "063113832", "jaimemabanque@laposte.fr", new Date(0));	
	}
	@Test(expected = IllegalArgumentException.class)
	public void testAdvisor_PhoneIsEmpty() {
		InitAdvisor("Bhank", "Gemma", "", "jaimemabanque@laposte.fr", new Date(0));	
	}
	@Test(expected = NullPointerException.class)
	public void testAdvisor_PhoneIsNull() {
		InitAdvisor("Bhank", "Gemma", null, "jaimemabanque@laposte.fr", new Date(0));	
	}
	@Test(expected = IllegalArgumentException.class)
	public void testAdvisor_MailIsEmpty() {
		InitAdvisor("Bhank", "Gemma", "063113832", "", new Date(0));	
	}
	@Test(expected = NullPointerException.class)
	public void testAdvisor_MailIsNull() {
		InitAdvisor("Bhank", "Gemma", "063113832", null, new Date(0));	
	}
	@Test(expected = NullPointerException.class)
	public void testAdvisor_DateAssignmentIsNull() {
		InitAdvisor("Bhank", "Gemma", "063113832", "jaimemabanque@laposte.fr", null);	
	}
	@Test(expected = IllegalArgumentException.class)
	public void testAdvisor_DateAssignmentInTheFuture() {
		Calendar cal = new GregorianCalendar(3017, Calendar.APRIL, 05, 12, 31, 15);
		InitAdvisor("Bhank", "Gemma", "063113832", "jaimemabanque@laposte.fr", cal.getTime());
	}
	
	@Test
	public void testGetName() {
		setup();
		assertEquals("Bhank", this.m_tested.getName());		
	}
	@Test
	public void testGetFirstName() {
		setup();
		assertEquals("Gemma", this.m_tested.getFirstName());		
	}
	@Test
	public void testGetPhone() {
		setup();
		assertEquals("063113832", this.m_tested.getPhone());		
	}
	@Test
	public void testGetEmail() {
		setup();
		assertEquals("jaimemabanque@laposte.fr", this.m_tested.getEmail());		
	}
	@Test
	public void testGetDateAssignment() {
		setup();
		Calendar cal = new GregorianCalendar(2015, Calendar.JULY, 31);
		assertEquals(cal.getTime(), this.m_tested.getAssignmentDate());		
	}
		
	private void InitAdvisor(String name, String firstName, String phone, 
			String email, Date dateAssignment){
		this.m_tested = new Advisor(dateAssignment, email, firstName, name, phone);
	}
	
	private Advisor m_tested;
}

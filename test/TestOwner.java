package com.capgemini.projet.java.jfx.controleur;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.projet.java.jfx.model.Owner;

public class TestOwner {
	
	
	@Before 			
	public void setup(){ 
		InitOwner("Jean Claude","VANDAME", "5555555555", dateNaiss(1960,Calendar.MAY,30), "vvvvvvv", "hgjjhjghjhh");
	}

	@Test(expected = NullPointerException.class)
	public void testOwner_NameIsNulle() {
		InitOwner(null,"VANDAME", "5555555555", dateNaiss(1960,Calendar.MAY,30), "vvvvvvv", "hgjjhjghjhh");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testOwner_NameIsEmpty() {
		InitOwner("","VANDAME", "5555555555",dateNaiss(1960,Calendar.MAY,30) , "vvvvvvv", "hgjjhjghjhh");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testOwner_FirstNameIsEmpty() {
		InitOwner("Jean Claude","", "5555555555",dateNaiss(1960,Calendar.MAY,30) , "vvvvvvv", "hgjjhjghjhh");
	}
	@Test(expected = NullPointerException.class)
	public void testOwner_FirstNameIsNull() {
		InitOwner("Jean Claude",null, "5555555555",dateNaiss(1960,Calendar.MAY,30) , "vvvvvvv", "hgjjhjghjhh");
	}
	@Test(expected = NullPointerException.class)
	public void testOwner_PhoneIsNull(){
		InitOwner("Jean Claude","VANDAME", null,dateNaiss(1960,Calendar.MAY,30) , "vvvvvvv", "hgjjhjghjhh");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testOwner_PhoneIsEmpty() {
		InitOwner("Jean Claude","VANDAME", "",dateNaiss(1960,Calendar.MAY,30) , "vvvvvvv", "hgjjhjghjhh");
	}
	@Test(expected = NullPointerException.class)
	public void testOwner_BirthdayIsNull(){
		InitOwner("Jean Claude","VANDAME", "5555555555", null , "vvvvvvv", "hgjjhjghjhh");
	}
	
	@Test(expected = NullPointerException.class)
	public void testOwner_LoginIsNull(){
		InitOwner("Jean Claude","VANDAME", "5555555555", dateNaiss(1960,Calendar.MAY,30) , null, "hgjjhjghjhh");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testOwner_LoginIsEmpty() {
		InitOwner("Jean Claude","VANDAME", "5555555555", dateNaiss(1960,Calendar.MAY,30), "", "hgjjhjghjhh");
	}
	@Test(expected = NullPointerException.class)
	public void testOwner_PassWordIsNull(){
		InitOwner("Jean Claude","VANDAME", "5555555555", dateNaiss(1960,Calendar.MAY,30) , "vvvvvvv", null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testOwner_PassWordIsEmpty() {
		InitOwner("Jean Claude","VANDAME", "5555555555", dateNaiss(1960,Calendar.MAY,30), "ghggggggg", "");
	}
	
	@Test
	public void testGetName() {
		setup();
		assertEquals("Jean Claude", this.m_tested.getName());
	
	}

	@Test
	public void testGetFirstName() {
		setup();
		assertEquals("VANDAME",this.m_tested.getFirstName());
	}

	@Test
	public void testGetPhon() {
		setup();
		assertEquals("5555555555",this.m_tested.getPhon());
	}

	@Test
	public void testGetBirthday() {
		setup();
		assertEquals(dateNaiss(1960,Calendar.MAY,30),this.m_tested.getBirthday());
	}

	@Test
	public void testGetLogin() {
		setup();
		assertEquals("vvvvvvv",this.m_tested.getLogin());
	}

	@Test
	public void testGetPassWord() {
		setup();
		assertEquals("hgjjhjghjhh",this.m_tested.getPassWord());
	}

	private Date dateNaiss(int year, int month, int day){
		Calendar bir = new GregorianCalendar(year, month, day);
		return bir.getTime();										//conversion du gregorien en date
		
	}
	private void InitOwner(String Name, String FirstName, String Phone,
			Date Birthday, String Login, String PassWord){
		this.m_tested = new Owner( Name,  FirstName,  Phone,
			 Birthday,  Login,  PassWord);
	}
	private Owner m_tested;

}
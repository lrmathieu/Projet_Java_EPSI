package com.capgemini.project.java.jfx.biz;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestAgency {
	
	@Test (expected = IllegalArgumentException.class) 
	public void testAgency_NameIsEmpty() { 
		new Agency(null, "12345"); 
	} 

	@Test (expected = IllegalArgumentException.class) 
	public void testAgency_CounterCodeIsEmpty() { 
		new Agency("foobar", null); 
	}
	
	@Test
	public void testGetName() {
		this.tested = new Agency("foobar", "12345");
		assertEquals("foobar", this.tested.getName());
	}
	
	@Test
	public void testSetName() {
		this.tested = new Agency("foobar", "12345");
		this.tested.setName("xyzzy");
		assertEquals("xyzzy", this.tested.getName());
	}
	
	@Test
	public void testGetCounterCode() {
		this.tested = new Agency("foobar", "12345");
		assertEquals("12345", this.tested.getCounterCode());
	}
	
	@Test
	public void testSetCounterCode() {
		this.tested = new Agency("foobar", "56789");
		this.tested.setCounterCode("12345");
		assertEquals("12345", this.tested.getCounterCode());
	}
	
	
	private Agency tested;
}
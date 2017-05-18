package com.capgemini.projet.java.jfx.controleur;

import org.junit.Test;

import com.capgemini.projet.java.jfx.model.CityZipCode;

public class TestCityZipCode {


	@Test(expected = NullPointerException.class)
	public void testCityZipCode_NullZipCode() {
		new CityZipCode(null, "Talence");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCityZipCode_ZipCodeIsempty() {
		new CityZipCode("", "Talence");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCityZipCode_CityIsEmpty() {
		new CityZipCode("toto", "");
	}
	@Test(expected = NullPointerException.class)
	public void testCityZipCode_CityIsNull() {
		new CityZipCode("33400", null);
	}

}
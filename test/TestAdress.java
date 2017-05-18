package com.capgemini.projet.java.jfx.controleur;
import org.junit.Test;

import com.capgemini.projet.java.jfx.model.Adress;

public class TestAdress {	
	@Test(expected = NullPointerException.class)
	public void testAdress_Lign1IsNull() {
		new Adress(null, "2322");
	}
	@Test(expected = IllegalArgumentException.class)
	public void testAdress_Lign1IsEmpty() {
		new Adress("", "");
	}

}
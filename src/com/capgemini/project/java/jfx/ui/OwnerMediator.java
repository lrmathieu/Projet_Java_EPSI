package com.capgemini.project.java.jfx.ui;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class OwnerMediator {

	public OwnerMediator(EntityManagerFactory emf) {
		if (emf==null) {
			throw new NullPointerException("EMF cannot be null !");
		}
		this.m_emf = emf;
	}
	
	public EntityManager createEntityManager() {
		return this.m_emf.createEntityManager();
	}
	
	private EntityManagerFactory m_emf;

}
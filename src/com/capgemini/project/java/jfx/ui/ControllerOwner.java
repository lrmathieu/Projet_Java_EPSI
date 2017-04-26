package com.capgemini.project.java.jfx.ui;

import java.util.List;
import javax.persistence.EntityManager;

import com.capgemini.project.java.jfx.biz.Owner;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ControllerOwner extends OwnerControllerBase{
	@FXML
	private TableView<Owner> listOwners;

	@Override
	public void initialize(Mediator mediator) {
		EntityManager em = mediator.createEntityManager();
		List<Owner> owners = em.createQuery("SELECT o FROM Owner o").getResultList();
		// Remplissage du tableview avec accounts
		this.listOwners.setItems(FXCollections.observableList(owners));
	}
}
	

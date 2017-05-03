package com.capgemini.project.java.jfx.ui;

import java.util.List;

import javax.persistence.EntityManager;

import com.capgemini.project.java.jfx.biz.Account; // MODELS
import com.capgemini.project.java.jfx.biz.Account0; // MODELS
import com.capgemini.project.java.jfx.biz.Advisor;
import com.capgemini.project.java.jfx.biz.Agency; // MODELS
import com.capgemini.project.java.jfx.biz.Bank; // MODELS
import com.capgemini.project.java.jfx.biz.CountryCode; // MODELS
import com.capgemini.project.java.jfx.biz.Owner; // MODELS
import com.capgemini.project.java.jfx.biz.PeriodicTransaction; // MODELS

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

public class AdvisorsOverviewController extends ControllerBase {

	@Override
	public void initialize(Mediator mediator) {
		EntityManager em = mediator.createEntityManager();
		List<Advisor> advisors = em.createQuery("SELECT a FROM Advisor a").getResultList();
		
		// Remplissage du tableview avec advisors
		this.listAdvisors.setItems(FXCollections.observableList(advisors));
	}
	
	@FXML
	private TableView<Advisor> listAdvisors;
	
	/*
	public AdvisorsOverviewController() {
		// TODO Auto-generated constructor stub
	}
	*/

}

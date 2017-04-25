package com.capgemini.project.java.jfx.ui;

import java.util.List;

import javax.persistence.EntityManager;

import com.capgemini.project.java.jfx.biz.Account; // MODELS
import com.capgemini.project.java.jfx.biz.Account0; // MODELS
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

public class AccountsOverviewController extends ControllerBase{
	
	@Override
	public void initialize(Mediator mediator) {
		EntityManager em = mediator.createEntityManager();
		List<Account> accounts = em.createQuery("SELECT a FROM Account a").getResultList();
		
		// Remplissage du tableview avec accounts
		this.listAccounts.setItems(FXCollections.observableList(accounts));
	}
	
	@FXML
	private TableView<Account> listAccounts;
		
	@FXML
	private Button btnApply;	
	
	@FXML
	private void handleBtnApply(ActionEvent event) {
		
	}
	
	@FXML
	private void handleBtnNew(ActionEvent event) {
		
	}
	
	
}

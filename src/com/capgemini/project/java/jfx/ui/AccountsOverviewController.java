package com.capgemini.project.java.jfx.ui;

import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class AccountsOverviewController extends ControllerBase{
	
	@Override
	public void initialize(Mediator mediator) {
		EntityManager em = mediator.createEntityManager();
		
		/*
		List<Bank> banks = em.createQuery("SELECT b FROM Bank b").getResultList();		
		// Remplissage du tableview avec banks
		this.listBanks.setItems(FXCollections.observableList(banks));
		*/
		
		/*
		List<Agency> agencies = em.createQuery("SELECT a FROM Agency a").getResultList();
		// Remplissage du tableview avec agencies
		this.listAgencies.setItems(FXCollections.observableList(agencies));
		*/
		
		/*
		List<Agency> agencies = em.createQuery("SELECT Account.accountNumber, Agency.name FROM Account INNER JOIN Agency ON Account.idAgency = Agency.id").getResultList();
		// Remplissage du tableview avec agencies
		this.listAgencies.setItems(FXCollections.observableList(agencies));
		*/
		
		List<Account> accounts = em.createQuery("SELECT a FROM Account a").getResultList();
		// Remplissage du tableview avec accounts
		this.listAccounts.setItems(FXCollections.observableList(accounts));	
	}
	
	@FXML
	private TableView<Account> listAccounts;
	
	@FXML
	private TableColumn<Account, String> accountNumberColumn;
	
	@FXML
	private Label accountNumberLabel;
	
	@FXML
	private TableView<Agency> listAgencies;
	
	@FXML
	private TableColumn<Agency, String> agencyNameColumn;
	
	/*@FXML
	private TableColumn<Account, String> agencyNameColumn;
	*/
	
	@FXML
	private Label agencyNameLabel;
	
	@FXML
	private TableView<Bank> listBanks;
	
	@FXML
	private TableColumn<Bank, String> bankNameColumn;
	
	@FXML
	private Label bankNameLabel;
		
	@FXML
	private Button btnApply;	
	
	@FXML
	private void handleBtnApply(ActionEvent event) {
		
	}
	
	@FXML
	private void handleBtnNew(ActionEvent event) {
		
	}
	
	
}

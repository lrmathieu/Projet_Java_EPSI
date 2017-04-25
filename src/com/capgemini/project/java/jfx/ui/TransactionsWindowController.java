package com.capgemini.project.java.jfx.ui;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.capgemini.project.java.jfx.biz.PeriodicTransaction;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TransactionsWindowController extends ControllerBase{
	@FXML private TableView<PeriodicTransaction> perTransTable;
	
    @FXML private TableColumn<PeriodicTransaction, Date> dateTransColumn;
    @FXML private TableColumn<PeriodicTransaction, String> wordingTransColumn;
    @FXML private TableColumn<PeriodicTransaction, Double> transValueColumn;
    @FXML private Label dateTransLabel;
    @FXML private Label wordingTransLabel;
    @FXML private Label transValueLabel;

	public TransactionsWindowController() {
		
	}
	
	@Override
	public void initialize(Mediator mediator) {
		EntityManager entMan = mediator.createEntityManager();
		List<PeriodicTransaction> perTrans = entMan.createQuery(
				"SELECT perTrans FROM PeriodicTransaction perTrans").getResultList();

		this.perTransTable.setItems(FXCollections.observableList(perTrans));		
	}
	
}

package com.capgemini.project.java.jfx.ui;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import com.capgemini.project.java.jfx.biz.PeriodicTransaction;
import com.capgemini.project.java.jfx.biz.Account;
import com.capgemini.project.java.jfx.biz.DateUtils;
import com.capgemini.project.java.jfx.biz.Frequency;
import com.capgemini.project.java.jfx.biz.TargetTransaction;
import com.capgemini.project.java.jfx.biz.TransactionType;
import com.capgemini.project.java.jfx.ui.Mediator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class TransactionsWindowController extends ControllerBase{
	@FXML private CheckBox chkDone;
	@FXML private TextField txtLabel;
	@FXML private DatePicker dateCreated;
	@FXML private ChoiceBox<TransactionType> choiceTransactionType;
	@FXML private ChoiceBox<Account> choiceAccount;
	@FXML private TextField transValue;
	@FXML private Button btnApply;
	@FXML private Button btnNew;
	@FXML private Label errAccount;
	@FXML private Label errCreated;
	@FXML private Label errLabel;
	@FXML private Label errTransType;
	@FXML private Label errTransValue;
	
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
		try {
			EntityManager em = mediator.createEntityManager();
			List<PeriodicTransaction> transactions = em.createNamedQuery(
					"PeriodicTransaction.findAll", 
					PeriodicTransaction.class
			).getResultList();

			//List<Account> accounts = em.createQuery("SELECT a.typeDescription FROM Account a").getResultList();
			List<TransactionType> transactiontypes = em.createNamedQuery(
					"TransactionType.findAll", 
					TransactionType.class
			).getResultList();
			List<Account> accounts = em.createNamedQuery(
					"Account.findAll", 
					Account.class
			).getResultList();
			this.choiceAccount.setItems(FXCollections.observableList(accounts));

			this.choiceTransactionType.setItems(FXCollections.observableList(transactiontypes));
			this.perTransTable.setItems(FXCollections.observableList(transactions));			
			this.perTransTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PeriodicTransaction>() {
				@Override
				public void changed(ObservableValue<? extends PeriodicTransaction> arg0, PeriodicTransaction oldVal, PeriodicTransaction newVal) {
					updateForm(newVal==null ? new PeriodicTransaction() : newVal);
				}
			});
			// force the field to be numeric only
		    this.transValue.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		            if (!newValue.matches("\\d*")) {
		            	transValue.setText(newValue.replaceAll("[^\\d]", "."));
		            }
		        }
		    });
			em.close();
		}
		catch(PersistenceException e) {
			this.btnNew.setDisable(true);
			this.processPersistenceException(e);
		}
		this.cur = new PeriodicTransaction();
		handleBtnNew(null);
		
//		EntityManager entMan = mediator.createEntityManager();
//		List<PeriodicTransaction> transactions = entMan.createQuery(
//				"SELECT transactions FROM PeriodicTransaction transactions").getResultList();
//
//		this.perTransTable.setItems(FXCollections.observableList(transactions));		
	}
	
	@FXML
	private void handleTextChanged(KeyEvent event) {
		this.fieldChanged();
	}
	
	@FXML
	private void handleFieldChanged(ActionEvent event) {
		this.fieldChanged();
	}
	private void fieldChanged() {
		this.resetErrors();
		this.dirty = true;
		this.btnApply.setDisable(false);
	}
	
	@FXML
	private void handleBtnApply(ActionEvent event) {
		if(this.saveForm()) {
			this.perTransTable.getSelectionModel().select(this.cur);
			this.perTransTable.scrollTo(this.cur);
		}
	}
	@FXML
	private void handleBtnNew(ActionEvent event) {
		this.perTransTable.getSelectionModel().select(null); // indirectly calls updateForm(new Task())
		this.btnNew.setDisable(true);
	}
	
	private boolean updateForm(PeriodicTransaction newTransaction) {
		this.resetErrors();
		if(this.dirty) {
			Alert alert  = new Alert(AlertType.CONFIRMATION, "La transaction est modifiée. Enregistrer les modifications ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			
			alert.showAndWait();
			
			ButtonType result = alert.getResult();
			
			if(result == ButtonType.CANCEL || (result == ButtonType.YES && !this.saveForm())) {
				return false;				
			}			
		}
		this.cur = newTransaction;
		this.txtLabel.setText(this.cur.getWording());
		this.dateCreated.setValue(DateUtils.DateToLocalDate(this.cur.getTransactionDate()));
		this.choiceAccount.setValue(this.cur.getAccount());
		this.choiceTransactionType.setValue(this.cur.getTransactionType());
		this.transValue.setText(String.valueOf(this.cur.getTransactionValue()));
		//this.choiceUser.setValue(this.cur.getUtilisateur());
		this.btnNew.setDisable(false);
		this.btnApply.setDisable(true);
		this.dirty = false;
		return true;
	}
	private boolean saveForm() {
		boolean isNew = this.cur.getId()==0;
		
		ObservableList<PeriodicTransaction> transactions = this.perTransTable.getItems();
		boolean err=false;
		
		if(this.dateCreated.getValue()==null) {
			this.errCreated.setVisible(true);
			err=true;
		}
		if(this.txtLabel.getText().isEmpty()) {
			this.errLabel.setVisible(true);
			err=true;
		}
		if(this.choiceAccount.getValue()==null) {
			this.errAccount.setVisible(true);
			err=true;
		}
		if(this.choiceTransactionType.getValue()==null) {
			this.errTransType.setVisible(true);
			err=true;
		}
		if(Double.parseDouble(this.transValue.getText())<=0d) {
			this.errTransValue.setVisible(true);
			err=true;
		}
		if(err) {
			return false;
		}
		this.cur.setTransactionDate(DateUtils.LocalDate2Date(this.dateCreated.getValue()));
		this.cur.setWording(this.txtLabel.getText());
		this.cur.setTransactionType(this.choiceTransactionType.getValue());
		this.cur.setTransactionValue(Double.parseDouble(this.transValue.getText()));
		this.cur.setAccount(this.choiceAccount.getValue());
		
		this.cur.setDayNumber(2);
		this.cur.setEndDateTransaction(this.cur.getTransactionDate());
		this.cur.setIdCategory(1);
		Frequency freq = new Frequency(1);
		this.cur.setFrequency(freq);
		TargetTransaction target = new TargetTransaction("test");
		this.cur.setTargetTransaction(target);

		try {
			EntityManager em = getMediator().createEntityManager();
			EntityTransaction transaction = em.getTransaction();

			try {					
				transaction.begin();
				em.persist(this.cur);
				transaction.commit();
				this.dirty = false;
				if(isNew) {
					transactions.add(this.cur);
				}
				else {
					transactions.set(transactions.indexOf(this.cur), this.cur);
				}
			}
			catch(RollbackException e) {
				return false;
			}
			finally {
				em.close();
			}
			return true;
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
			return false;
		}
	}
	private void resetErrors() {
		for(Label l : new Label[]{ errCreated, errLabel, errTransValue, errTransType, errAccount }) {
			l.setVisible(false);
		}
	}
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	    
	
	private PeriodicTransaction cur = null;
	private boolean dirty = false;
	
}

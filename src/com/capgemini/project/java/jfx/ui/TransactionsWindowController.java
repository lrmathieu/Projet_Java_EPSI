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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

/**
 *  
 *  Principalement inspire du code TaskListController de Sylvain Labasse
 */
public class TransactionsWindowController extends ControllerBase{
	@FXML private TextField txtTransaction;
	@FXML private DatePicker dateTransaction;
	@FXML private ChoiceBox<TransactionType> choiceTransactionType;
	@FXML private ChoiceBox<Account> choiceAccount;
	@FXML private ChoiceBox<TargetTransaction> choiceTarget;
	@FXML private TextField transValue;
	@FXML private Button btnApply;
	@FXML private Button btnNew;
	@FXML private Label errAccount;
	@FXML private Label errDateTrans;
	@FXML private Label errTargetTrans;
	@FXML private Label errTransType;
	@FXML private Label errTransValue;
	@FXML private Label errTxtTrans;

	@FXML private TableView<PeriodicTransaction> listTransactions;
	
    @FXML private TableColumn<PeriodicTransaction, Date> dateTransColumn;
    @FXML private TableColumn<PeriodicTransaction, String> wordingTransColumn;
    @FXML private TableColumn<PeriodicTransaction, Double> transValueColumn;
    @FXML private Label dateTransLabel;
    @FXML private Label wordingTransLabel;
    @FXML private Label transValueLabel;
	
	@Override
	public void initialize(Mediator mediator) {
		try {
			EntityManager em = mediator.createEntityManager();
			
			//DRY sur instructions qui suivent? A factoriser?
			List<PeriodicTransaction> transactions = em.createNamedQuery(
					"PeriodicTransaction.findAll", PeriodicTransaction.class
			).getResultList();
			List<TransactionType> transactiontypes = em.createNamedQuery(
					"TransactionType.findAll", TransactionType.class
			).getResultList();
			List<Account> accounts = em.createNamedQuery(
					"Account.findAll", Account.class
			).getResultList();
			List<TargetTransaction> targets = em.createNamedQuery(
					"TargetTransaction.findAll", TargetTransaction.class
			).getResultList();
			
			this.choiceAccount.setItems(FXCollections.observableList(accounts));
			this.choiceTarget.setItems(FXCollections.observableList(targets));
			this.choiceTransactionType.setItems(FXCollections.observableList(transactiontypes));
			this.listTransactions.setItems(FXCollections.observableList(transactions));			
			this.listTransactions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PeriodicTransaction>() {
				@Override
				public void changed(ObservableValue<? extends PeriodicTransaction> arg0, PeriodicTransaction oldVal, PeriodicTransaction newVal) {
					updateForm(newVal==null ? new PeriodicTransaction() : newVal);
				}
			});
			
			// To force the text in the field "transvalue" to be numeric only:
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
			this.listTransactions.getSelectionModel().select(this.cur);
			this.listTransactions.scrollTo(this.cur);
		}
	}
	@FXML
	private void handleBtnNew(ActionEvent event) {
		this.listTransactions.getSelectionModel().select(null); // indirectly calls updateForm(new PeriodicTransaction())
		this.btnNew.setDisable(true);
	}
	
	private boolean updateForm(PeriodicTransaction newTransaction) {
		this.resetErrors();
		if(this.dirty) {
			Alert alert  = new Alert(AlertType.CONFIRMATION, "The transaction is modified. Save the modifications?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			
			alert.showAndWait();
			
			ButtonType result = alert.getResult();
			
			if(result == ButtonType.CANCEL || (result == ButtonType.YES && !this.saveForm())) {
				return false;				
			}			
		}
		this.cur = newTransaction;
		this.txtTransaction.setText(this.cur.getWording());
		this.dateTransaction.setValue(DateUtils.DateToLocalDate(this.cur.getTransactionDate()));
		this.choiceAccount.setValue(this.cur.getAccount());
		this.choiceTarget.setValue(this.cur.getTargetTransaction());
		this.choiceTransactionType.setValue(this.cur.getTransactionType());
		this.transValue.setText(String.valueOf(this.cur.getTransactionValue()));
		this.btnNew.setDisable(false);
		this.btnApply.setDisable(true);
		this.dirty = false;
		return true;
	}
	private boolean saveForm() {
		boolean isNew = this.cur.getId()==0;
		
		ObservableList<PeriodicTransaction> transactions = this.listTransactions.getItems();
		boolean err=false;
		
		if(this.dateTransaction.getValue()==null) {
			this.errDateTrans.setVisible(true);
			err=true;
		}
		if(this.txtTransaction.getText().isEmpty()) {
			this.errTxtTrans.setVisible(true);
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
		if(this.choiceTarget.getValue()==null) {
			this.errTargetTrans.setVisible(true);
			err=true;
		}
		if(this.transValue.getText().isEmpty() || isDouble(this.transValue.getText())==false || Double.parseDouble(this.transValue.getText())<=0d ) {
			this.errTransValue.setVisible(true);
			err=true;
		}
		if(err) {
			return false;
		}
		this.cur.setAccount(this.choiceAccount.getValue());
		this.cur.setTargetTransaction(this.choiceTarget.getValue());
		this.cur.setTransactionDate(DateUtils.LocalDate2Date(this.dateTransaction.getValue()));
		this.cur.setTransactionType(this.choiceTransactionType.getValue());
		this.cur.setTransactionValue(Double.parseDouble(this.transValue.getText()));
		this.cur.setWording(this.txtTransaction.getText());

		this.cur.setDayNumber(2);
		this.cur.setEndDateTransaction(this.cur.getTransactionDate());
		this.cur.setIdCategory(1);
		this.cur.setIdFrequency(1);

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
		
		for(Label l : new Label[]{ errDateTrans, errTxtTrans, errTransValue, errTransType, errAccount, errTargetTrans }) {
			l.setVisible(false);
		}
	}
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	
    private boolean isDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
//	private static boolean isParsableAsDouble(final String s) {
//	    try {
//	        Double.valueOf(s);
//	        return true;
//	    } catch (NumberFormatException numberFormatException) {
//	        return false;
//	    }
//	}
	    
	private PeriodicTransaction cur = null;
	private boolean dirty = false;
	
}

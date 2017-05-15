package com.capgemini.project.java.jfx.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import com.capgemini.project.java.jfx.biz.Account;
import com.capgemini.project.java.jfx.biz.PeriodicTransaction;

//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class DetailsTransactionsByAccountController extends ControllerBase {

	@FXML private ChoiceBox<Account> choiceAccount;
	@FXML private ChoiceBox<String> selectAccount;
	@FXML private Label errAccount;
	@FXML private Label totalTransactions;

	@FXML private TableView<PeriodicTransaction> listTransactions;
	
    @FXML private TableColumn<PeriodicTransaction, Date> dateTransColumn;
    @FXML private TableColumn<PeriodicTransaction, String> wordingTransColumn;
    @FXML private TableColumn<PeriodicTransaction, Double> transValueColumn;
	
    /**
     * TransactionsWindowController initializer
     * @param mediator
     */
	@Override
	public void initialize(Mediator mediator) {
		try {
			EntityManager em = mediator.createEntityManager();
			
			List<PeriodicTransaction> transactions = em.createNamedQuery(
					"PeriodicTransaction.findAll", PeriodicTransaction.class
			).getResultList();
/*			List<Account> accounts = em.createNamedQuery(
					"Account.findAll", Account.class
			).getResultList();*/
		
			// Initialization for select accounts
			List<String> accountNumbers = em.createQuery("SELECT a.accountNumber FROM Account a", String.class).getResultList(); //SELECT a.accountNumber
			accountNumbers.add(0, "All Accounts");
			this.selectAccount.setItems(FXCollections.observableList(accountNumbers));
			this.selectAccount.getSelectionModel().selectFirst();
			
			this.listTransactions.setItems(FXCollections.observableList(transactions));			
			
			// Initialization for total balance calculation based on all accounts
			Double sumInitialAmounts = em.createQuery("SELECT SUM(a.firstTotal) FROM Account a", Double.class).getSingleResult();
			Double sumTransactions = em.createQuery("SELECT SUM(t.transactionValue) FROM PeriodicTransaction t", Double.class).getSingleResult();
			if(sumInitialAmounts != null && sumTransactions != null){
				this.total = sumInitialAmounts + sumTransactions;
			} 
			else{
				this.total = Double.parseDouble("0");	
			}
			setTotalBalanceInLabel(this.total);
			
			em.close();
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
		}
	}
	
    /**
     * When the user set new text in text fields 
     * @param event
     */
	@FXML
	private void handleTextChanged(KeyEvent event) {
		this.fieldChanged();
	}
	@FXML
	private void handleFieldChanged(ActionEvent event) {
		this.fieldChanged();
	}
	
    /**
     * When a field is changed
     */
	private void fieldChanged() {
	}

	@FXML
	private void handleAccountSelectFieldChanged(ActionEvent event) {
		this.accountSelectFieldChanged();
	}
	
	private void accountSelectFieldChanged() {
		try{
			EntityManager em = getMediator().createEntityManager();
			
			if(!(this.selectAccount.getValue().equals("All Accounts"))){
				
				TypedQuery<Account>queryGetSelectedAccount = em.createQuery(
						"SELECT a FROM Account a WHERE a.accountNumber=:selectedAccount", Account.class);
				queryGetSelectedAccount.setParameter("selectedAccount", this.selectAccount.getValue());
				
				//Account selectedAccount = queryGetSelectedAccount.getSingleResult();
				Integer idSelectedAccount = queryGetSelectedAccount.getSingleResult().getId();
				Double initialAmount = queryGetSelectedAccount.getSingleResult().getFirstTotal();
				Date dateInitialAmount = queryGetSelectedAccount.getSingleResult().getCreationDate();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateInitialAmount);
				PeriodicTransaction openAccount = new PeriodicTransaction(
						"Initial amount placed in the account", initialAmount, cal.get(Calendar.DAY_OF_MONTH), dateInitialAmount, dateInitialAmount);
				
		        //Account accountView = em.find(Account.class, this.cur.getAccount());
				//Double d = em.find(Double.class, this.selectAccount.getValue());
				TypedQuery<PeriodicTransaction>queryGetTransactionsFromSelectedAccount = em.createQuery(
						"SELECT t FROM PeriodicTransaction t WHERE t.account.id=:idAcc", PeriodicTransaction.class
				);
				queryGetTransactionsFromSelectedAccount.setParameter("idAcc", idSelectedAccount);
				List<PeriodicTransaction> newListTransactions = queryGetTransactionsFromSelectedAccount.getResultList();
				newListTransactions.add(0, openAccount);
				
				for (int i=0; i<this.listTransactions.getItems().size(); i++) {
					this.listTransactions.getItems().clear();
			    }	
				this.listTransactions.setItems(FXCollections.observableList(newListTransactions));
				
//				TypedQuery<Double>queryTransactionsFromSelectedAccount = em.createQuery(
//				"SELECT SUM(t.transactionValue) FROM PeriodicTransaction t WHERE t.account.id=:idAcc", 
//				Double.class);
//				queryTransactionsFromSelectedAccount.setParameter("idAcc", idSelectedAccount);
//				Double sumTransFromSelectedAccount = queryTransactionsFromSelectedAccount.getSingleResult();

				TypedQuery<Double>queryTransFromSelectedAccount = em.createQuery(
					"SELECT t.transactionValue FROM PeriodicTransaction t WHERE t.account.id=:idAcc", 
					Double.class
				);
				queryTransFromSelectedAccount.setParameter("idAcc", idSelectedAccount);
				Double sumTransFromSelectedAccount = queryTransFromSelectedAccount.getResultList().stream().mapToDouble(Double::doubleValue).sum();
				this.total = initialAmount + sumTransFromSelectedAccount;
				setTotalBalanceInLabel(this.total);

			}
			else{
				List<PeriodicTransaction> allTransactions = em.createNamedQuery(
						"PeriodicTransaction.findAll", PeriodicTransaction.class).getResultList();
				this.listTransactions.setItems(FXCollections.observableList(allTransactions));
			}
			em.close();
		}
		catch(PersistenceException e) {
			this.processPersistenceException(e);
		}	
	}
	
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}

    private void setTotalBalanceInLabel(Double value){ 
		this.totalTransactions.setText("Total Balance: " + String.valueOf(this.total) + " €");
    }
    
	private Double total;	

}

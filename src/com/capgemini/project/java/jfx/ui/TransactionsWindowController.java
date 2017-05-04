package com.capgemini.project.java.jfx.ui;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import com.capgemini.project.java.jfx.biz.PeriodicTransaction;
import com.capgemini.project.java.jfx.biz.Account;
import com.capgemini.project.java.jfx.biz.DateUtils;
import com.capgemini.project.java.jfx.biz.TargetTransaction;
import com.capgemini.project.java.jfx.biz.TransactionType;
import com.capgemini.project.java.jfx.ui.Mediator;

import javafx.application.Platform;
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
 *  FXML Controller for the Transactions window of the application
 *  Inspired by Sylvain Labasse 's script TaskListController 
 */
public class TransactionsWindowController extends ControllerBase{
	@FXML private TextField txtTransaction;
	@FXML private DatePicker dateTransaction;
	@FXML private ChoiceBox<TransactionType> choiceTransactionType;
	@FXML private ChoiceBox<Account> choiceAccount;
	@FXML private ChoiceBox<TargetTransaction> choiceTarget;
	@FXML private TextField transValue;
	@FXML private Button btnApply;
	@FXML private Button btnDelete;
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
	
    /**
     * TransactionsWindowController initializer
     * @param mediator
     */
	@Override
	public void initialize(Mediator mediator) {
		try {
			EntityManager em = mediator.createEntityManager();
			
			//DRY sur instructions qui suivent? A factoriser?
			List<PeriodicTransaction> transactions = em.createNamedQuery(
					"PeriodicTransaction.findAll", PeriodicTransaction.class
			).getResultList();
			//List<PeriodicTransaction> transactions = em.createQuery("SELECT t FROM PeriodicTransaction t").getResultList();
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
			
			//Platform.runLater(() -> 
			this.listTransactions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PeriodicTransaction>() {
				@Override
				public void changed(ObservableValue<? extends PeriodicTransaction> arg0, PeriodicTransaction oldVal, PeriodicTransaction newVal) {
					updateForm(newVal==null ? new PeriodicTransaction() : newVal);
				}
			});
			
			/**
			 *  To force the text in the field "transvalue" to be numeric only with a dot:
			 */
		    this.transValue.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		            if (!newValue.matches("(^\\d+(\\.\\d+)*$)")){ 
		            	transValue.setText(newValue.replaceAll("[^\\d+(\\.\\d+)*$]", "")); 
		            }
		        }
		    });
			em.close();
		}
		catch(PersistenceException e) {
			this.btnNew.setDisable(true);
			this.btnDelete.setDisable(true);
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

//  //code suivant: chercher a afficher les details de transactions par compte(Account)
//	@FXML
//	private void handleAccountFieldChanged(ActionEvent event) {
//		this.fieldChanged();
//	}
//	private void accountFieldChanged() {
//		this.resetErrors();
//		EntityManager em = getMediator().createEntityManager();
//		
//		List<Account> account = em.createNamedQuery(
//				"Account.findAll", Account.class
//		).getResultList();
//
//	}
	
	/**
     * Called when the user click on "Apply" button. Add the new PeriodicTransaction on Database with the values 
     * chosen by the user in the different TextField and ChoiceBox
     * @param event 
     */
	@FXML
	private void handleBtnApply(ActionEvent event) {
		if(this.saveForm()) {
			this.listTransactions.getSelectionModel().select(this.cur);
			this.listTransactions.scrollTo(this.cur);
		}
	}

	/**
     * Called when the user click on "Delete" button
     * Delete the selected existing PeriodicTransaction from the Database
     * @param event 
     */
    @FXML
    private void handleBtnDelete(ActionEvent event) {
    	EntityManager em = getMediator().createEntityManager();
        PeriodicTransaction deletedTrans = em.find(PeriodicTransaction.class, this.cur.getId());
        
    	Alert alert  = new Alert(AlertType.CONFIRMATION, 
				"The transaction is going to be deleted. Do you want to continue?", 
				ButtonType.OK, ButtonType.CANCEL
		);
		alert.showAndWait();
		ButtonType result = alert.getResult();
        try {            
            if(result == ButtonType.OK) {
                em.getTransaction().begin();
                em.remove(deletedTrans);
                em.getTransaction().commit();
                this.listTransactions.getSelectionModel().select(this.cur);
                this.listTransactions.getItems().remove(this.cur);
            }
         }
         catch(Exception e){
     		new Alert(AlertType.ERROR, "An error as occured", ButtonType.OK).showAndWait();
         }
        this.btnDelete.setDisable(true);
     }

		
	/**
     * Called when the user click on "New Transaction" button
     * Initialize fields in order to add a new PeriodicTransaction on the Database
     * @param event 
     */
	@FXML
	private void handleBtnNew(ActionEvent event) {
		//Platform.runLater(() -> 
		this.listTransactions.getSelectionModel().select(null); // indirectly calls updateForm(new PeriodicTransaction())
		//);
		this.btnNew.setDisable(true);
		this.btnDelete.setDisable(true);
	}
	
	private boolean updateForm(PeriodicTransaction newTransaction) {
			this.resetErrors();
			if(this.dirty) {
				new Alert(AlertType.INFORMATION, "The transaction was being modified but will not be saved", ButtonType.OK).showAndWait(); //.showAndWait();

//				Alert alert  = new Alert(AlertType.CONFIRMATION, 
//						"The transaction is modified. Save the modifications?", 
//						ButtonType.YES, ButtonType.NO, ButtonType.CANCEL
//				);
//				alert.showAndWait();
//				
//				ButtonType result = alert.getResult();			
//				if(result == ButtonType.CANCEL || (result == ButtonType.YES && !this.saveForm())) {
//					return false;				
//				}

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
			this.btnDelete.setDisable(false);
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

		// initialistation des autres champs necessaires. 
		// Ils seront de valeur fixe pour le moment
		this.cur.setDayNumber(2);
		this.cur.setEndDateTransaction(this.cur.getTransactionDate());
		this.cur.setIdCategory(1);
		this.cur.setIdFrequency(1);

		try {
			EntityManager em = getMediator().createEntityManager();
			EntityTransaction transaction = em.getTransaction();

			try {					
				transaction.begin();

				if(isNew) {
					em.persist(this.cur);
					transaction.commit();
					this.dirty = false;
					transactions.add(this.cur);
				}
				else {	
					TypedQuery<PeriodicTransaction>query = em.createQuery(
						"UPDATE PeriodicTransaction t "
						+ "SET t.wording=:wordingToUpdate,"
							+ "t.transactionValue=:valTransToUpdate,"
							+ "t.dayNumber=:dayNumberToUpdate,"
							+ "t.transactionDate=:dateTransToUpdate, "
							+ "t.endDateTransaction=:endDateTransToUpdate "	
						+ "WHERE t.id=:idTransToUpdate", PeriodicTransaction.class);
					query.setParameter("wordingToUpdate", this.cur.getWording());
					query.setParameter("valTransToUpdate", this.cur.getTransactionValue());
					query.setParameter("dayNumberToUpdate", this.cur.getDayNumber());
					query.setParameter("dateTransToUpdate", this.cur.getTransactionDate());
					query.setParameter("endDateTransToUpdate", this.cur.getEndDateTransaction());
					query.setParameter("idTransToUpdate", this.cur.getId());				
					
					query.executeUpdate();
//					TypedQuery<PeriodicTransaction>queryTarTrans = em.createQuery(
//							"UPDATE PeriodicTransaction t SET t.getTargetTransaction=:targetTransToUpdate WHERE t.id=:idTransToUpdate", PeriodicTransaction.class);
//					queryTarTrans.setParameter("targetTransToUpdate", this.cur.getTargetTransaction());
//					queryTarTrans.setParameter("idTransToUpdate", this.cur.getId());
//					TypedQuery<PeriodicTransaction>queryTypTrans = em.createQuery(
//							"UPDATE PeriodicTransaction t SET t.idTypeTransaction=:typeTransToUpdate WHERE t.id=:idTransToUpdate", PeriodicTransaction.class);
//					queryTypTrans.setParameter("typeTransToUpdate", this.cur.getTransactionType().getId());
//					queryTypTrans.setParameter("idTransToUpdate", this.cur.getId());
//					TypedQuery<PeriodicTransaction>queryAcc = em.createQuery(
//							"UPDATE PeriodicTransaction t SET t.idAccount=:accountToUpdate WHERE t.id=:idTransToUpdate", PeriodicTransaction.class);
//					queryAcc.setParameter("accountToUpdate", this.cur.getAccount().getId());
//					queryAcc.setParameter("idTransToUpdate", this.cur.getId());
					
//					queryTarTrans.executeUpdate();
//					queryTypTrans.executeUpdate();
//					queryAcc.executeUpdate();
					
					transaction.commit();
					
					this.dirty = false;
					transactions.set(transactions.indexOf(this.cur), this.cur);//);
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
	
	/**
     * Check if a String s is a double or not
     * @param s a String
     * @return true or false if s cannot be converted to Double
     */
    private boolean isDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
	    
	private PeriodicTransaction cur = null;
	private boolean dirty = false;
	
}

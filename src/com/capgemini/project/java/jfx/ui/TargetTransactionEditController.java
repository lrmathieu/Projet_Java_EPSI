package com.capgemini.project.java.jfx.ui;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import com.capgemini.project.java.jfx.biz.PeriodicTransaction;
import com.capgemini.project.java.jfx.biz.TargetTransaction;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class TargetTransactionEditController extends ControllerBase{

	@FXML private TextField txtNameTargetTransaction;
	@FXML private TextField txtIban;
	@FXML private Button btnApply;
	@FXML private Button btnDelete;
	@FXML private Button btnNew;
	@FXML private Label errIban;
	@FXML private Label errNameTargetTransaction;

	@FXML private TableView<TargetTransaction> listTargetTransactions;
	
    @FXML private TableColumn<PeriodicTransaction, String> targetTransColumn;
    @FXML private TableColumn<PeriodicTransaction, String> ibanTransColumn;
	
    /**
     * TargetTransactionEditController initializer
     * @param mediator
     */
	@Override
	public void initialize(Mediator mediator) {
		try {
			EntityManager em = mediator.createEntityManager();
			
			List<TargetTransaction> targets = em.createNamedQuery(
					"TargetTransaction.findAll", TargetTransaction.class
			).getResultList();
			
			this.listTargetTransactions.setItems(FXCollections.observableList(targets));	
			
			this.listTargetTransactions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TargetTransaction>() {
				@Override
				public void changed(ObservableValue<? extends TargetTransaction> arg0, TargetTransaction oldVal, TargetTransaction newVal) {
					updateForm(newVal==null ? new TargetTransaction() : newVal);
				}
			});
			em.close();
		}
		catch(PersistenceException e) {
			this.btnNew.setDisable(true);
			this.btnDelete.setDisable(true);
			this.processPersistenceException(e);
		}
		this.cur = new TargetTransaction();
		handleBtnNew(null);
	}
	
    /**
     * When the user set new text in text fields 
     * @param event
     */
	@FXML
	private void handleTextChanged(KeyEvent event) {
		this.fieldChanged();
	}
	
    /**
     * When the user makes new choice in choice boxes 
     * @param event
     */
	@FXML
	private void handleFieldChanged(ActionEvent event) {
		this.fieldChanged();
	}
	
    /**
     * When a field is changed
     */
	private void fieldChanged() {
		this.resetErrors();
		this.dirty = true;
		this.btnApply.setDisable(false);
	}
	
	/**
     * Called when the user click on "Apply" button. Add the new TargetTransacation on Database with the values 
     * chosen by the user in the different TextField and ChoiceBox
     * @param event 
     */
	@FXML
	private void handleBtnApply(ActionEvent event) {
		if(this.saveForm()) {
			this.listTargetTransactions.getSelectionModel().select(this.cur);
			this.listTargetTransactions.scrollTo(this.cur);
		}
	}

	/**
     * Called when the user click on "Delete" button. Delete the selected TargetTransaction from the Database
     * @param event 
     */
    @FXML
    private void handleBtnDelete(ActionEvent event) {
    	EntityManager em = getMediator().createEntityManager();
    	TargetTransaction deletedTarget = em.find(TargetTransaction.class, this.cur.getId());
        
    	Alert alert  = new Alert(AlertType.CONFIRMATION, 
				"The target transaction is going to be deleted. Do you want to continue?", 
				ButtonType.OK, ButtonType.CANCEL
		);
		alert.showAndWait();
		ButtonType result = alert.getResult();
        try {            
            if(result == ButtonType.OK) {
                em.getTransaction().begin();
                em.remove(deletedTarget);
                em.getTransaction().commit();
                this.listTargetTransactions.getSelectionModel().select(this.cur);
                this.listTargetTransactions.getItems().remove(this.cur);
            }
         }
         catch(Exception e){
     		new Alert(AlertType.ERROR, "An error as occured", ButtonType.OK).showAndWait();
         }
        this.btnDelete.setDisable(true);
     }

		
	/**
     * Called when the user click on "New Target Transaction" button
     * Initialize fields in order to add a new TargetTransaction on the Database
     * @param event 
     */
	@FXML
	private void handleBtnNew(ActionEvent event) {
		this.listTargetTransactions.getSelectionModel().select(null); // indirectly calls updateForm(new TargetTransaction())
		this.txtIban.setText(null);
		this.btnNew.setDisable(true);
		this.btnDelete.setDisable(true);
	}
	
	/**
     * Update the TargetTransaction selected by the user with the cursor of the TableView and check if it is ready to be updated
     * @param newTransaction = new TargetTransaction selected by the user
     * @return a boolean true or false
     */
	private boolean updateForm(TargetTransaction newTargetTransaction) {
			this.resetErrors();
			if(this.dirty) {
				new Alert(AlertType.INFORMATION, "The target transaction was being modified but will not be saved", ButtonType.OK).showAndWait();

			}
			this.cur = newTargetTransaction;
			this.txtNameTargetTransaction.setText(this.cur.getSummary());			
			this.txtIban.setText(String.valueOf(this.cur.getIban()));
			this.btnNew.setDisable(false);
			this.btnApply.setDisable(true);
			this.btnDelete.setDisable(false);
			this.dirty = false;
			return true;
	}
	
	/**
     * Check if informations set by the user are valid before saving them in database
     * @return a boolean true or false
     */
	private boolean saveForm() {
		boolean isNew = this.cur.getId()==0;
		
		ObservableList<TargetTransaction> targets = this.listTargetTransactions.getItems();
		boolean err=false;
		
		if(this.txtNameTargetTransaction.getText().isEmpty()) {
			this.errNameTargetTransaction.setVisible(true);
			err=true;
		}
/*		if(this.txtIban.getText()==null) {
			this.errIban.setVisible(true);
			err=true;
		}*/
		if(err) {
			return false;
		}
		
		this.cur.setSummary(this.txtNameTargetTransaction.getText());
		this.cur.setIban(this.txtIban.getText());

		try {
			EntityManager em = getMediator().createEntityManager();
			EntityTransaction transaction = em.getTransaction();

			try {					
				transaction.begin();

				if(isNew) {
					em.persist(this.cur);
					transaction.commit();
					this.dirty = false;
					targets.add(this.cur);
				}
				else {	
					TypedQuery<TargetTransaction>query = em.createQuery(
						"UPDATE TargetTransaction t "
						+ "SET t.summary=:summaryToUpdate,"
							+ "t.iban=:ibanToUpdate "
							+ "WHERE t.id=:idTargetTransToUpdate", 
							TargetTransaction.class);
					query.setParameter("summaryToUpdate", this.cur.getSummary());
					query.setParameter("ibanToUpdate", this.cur.getIban());
					query.setParameter("idTargetTransToUpdate", this.cur.getId());				
					query.executeUpdate();
					
					transaction.commit();
					
					this.dirty = false;
					targets.set(targets.indexOf(this.cur), this.cur);//);
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
	
	/**
	 * Reset visibility of error messages in the window
	 */
	private void resetErrors() {	
		for(Label l : new Label[]{ errNameTargetTransaction, errIban}) {
			l.setVisible(false);
		}
	}
	
	/**
	 * Called when PersistenceException. Set new alert and give more details to the user about the error
	 * @param e
	 */
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}
	
	/*public ObservableList<TargetTransaction> getListTargets(Mediator mediator){
		
		try {
			EntityManager em = mediator.createEntityManager();
			
			List<TargetTransaction> targets = em.createNamedQuery(
					"TargetTransaction.findAll", TargetTransaction.class
			).getResultList();
			
			this.listTargetTransactions.setItems(FXCollections.observableList(targets));			
			em.close();	
		}
		catch(PersistenceException e) {
			
		}
		return this.listTargetTransactions.getItems();
		
	}*/

	private TargetTransaction cur = null;
	private boolean dirty = false;	
}

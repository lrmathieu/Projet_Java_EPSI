package com.capgemini.project.java.jfx.ui;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import com.capgemini.project.java.jfx.biz.Owner;
import com.capgemini.project.java.jfx.biz.DateUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class ControllerOwner extends ControllerBase{
	@FXML
	private TableView<Owner> listOwners;
	@FXML private Button btnAdd;
	@FXML private Button btnNew;
	@FXML private DatePicker dateBirth;
	@FXML private Label errCreated;
	@FXML private TextField txtLabel;
	@FXML private Label errLabel;
	@FXML private TextField txtLabelFn;
	@FXML private Label errLabelFn;
	@FXML private TextField txtLabelPh;
	@FXML private Label errLabelPh;
	@FXML private TextField txtLabelLog;
	@FXML private Label errLabelLog;
	@FXML private PasswordField txtLabelPw;
	@FXML private Label errLabelPw;

	@Override
	@SuppressWarnings("unchecked")
	public void initialize(Mediator mediator) {
		
		try{
			EntityManager em = mediator.createEntityManager();
			List<Owner> owners = em.createQuery("SELECT o FROM Owner o").getResultList();
			this.listOwners.setItems(FXCollections.observableList(owners));
			this.listOwners.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Owner>() {
				@Override
				public void changed(ObservableValue<? extends Owner> arg0, Owner oldVal, Owner newVal) {
					updateForm(newVal==null ? new Owner() : newVal);
				}
			});
			// To force the text in the field "txtLabelPh" to be numeric only:
		    this.txtLabelPh.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		            if (!newValue.matches("\\d*")) {
		            	txtLabelPh.setText(newValue.replaceAll("[^\\d]", ""));
		            }
		        }
		    });
			em.close();
		}
		catch(PersistenceException e) {
			this.btnNew.setDisable(true);
			this.processPersistenceException(e);
		}
		this.cur = new Owner();
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
		this.btnAdd.setDisable(false);
	}
	@FXML
	private void handleBtnAdd(ActionEvent event) {
		if(this.saveForm()) {
			this.listOwners.getSelectionModel().select(this.cur);
			this.listOwners.scrollTo(this.cur);
		}
	}
	
	
	@FXML
	private void handleBtnNew(ActionEvent event) {
		this.listOwners.getSelectionModel().select(null); // indirectly calls updateForm(new Task())
		this.btnNew.setDisable(true);
	}
	
	private boolean updateForm(Owner newOwner) {
		this.resetErrors();
		if(this.dirty) {
			Alert alert  = new Alert(AlertType.CONFIRMATION, "Voulez-vous ajouter un nouvel utilisateur ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			
			alert.showAndWait();
			
			ButtonType result = alert.getResult();
			
			if(result == ButtonType.CANCEL || (result == ButtonType.YES && !this.saveForm())) {
				return false;				
			}			
		}
		
		//Affichage des champs de la table owner dans table view
		this.cur = newOwner;
		this.txtLabel.setText(this.cur.getName());
		this.txtLabelFn.setText(this.cur.getFirstName());
		this.txtLabelPh.setText(this.cur.getPhone());
		this.dateBirth.setValue(DateUtils.DateToLocalDate(this.cur.getBirthday()));
		//this.txtLabelPw.setText(this.cur.getPassword());
		//this.txtLabelLog.setText(this.cur.getLogin());
		this.btnNew.setDisable(false);
		this.btnAdd.setDisable(true);
		this.dirty = false;
		return true;
		
	}
	
	
	private boolean saveForm() {
		boolean isNew = this.cur.getId()==0;
		ObservableList<Owner> owners = this.listOwners.getItems();
		boolean err=false;
		
		if(this.dateBirth.getValue()==null) {
			this.errCreated.setVisible(true);
			err=true;
		}
		if(this.txtLabel.getText().isEmpty()) {
			this.errLabel.setVisible(true);
			err=true;
		}
		if(this.txtLabelFn.getText().isEmpty()){
			this.errLabelFn.setVisible(true);
			err=true;
		}
		if(this.txtLabelPh.getText().isEmpty()){
			this.errLabelPh.setVisible(true);
			err=true;
		}
		if(this.txtLabelLog.getText().isEmpty()){
			this.errLabelLog.setVisible(true);
			err=true;
		}
		if(this.txtLabelPw.getText().isEmpty()){
			this.errLabelPw.setVisible(true);
			err=true;
		}	
		
	
		if(err) {
			return false;
		}
		//Anjout dans la table owner
		this.cur.setBirthday(DateUtils.LocalDate2Date(this.dateBirth.getValue()));
		this.cur.setName(this.txtLabel.getText());
		this.cur.setFirstName(this.txtLabelFn.getText());
		this.cur.setPhone(this.txtLabelPh.getText());
		this.cur.setPassword(this.txtLabelPw.getText());
		this.cur.setLogin(this.txtLabelLog.getText());
		this.cur.setIdAddress(1);
		
	
	
		try {
			EntityManager em = getMediator().createEntityManager();
			EntityTransaction transaction = em.getTransaction();

			try {					
				transaction.begin();
				em.persist(this.cur);
				transaction.commit();
				this.dirty = false;
				if(isNew) {
					owners.add(this.cur);
				}
				else {
					owners.set(owners.indexOf(this.cur), this.cur);
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
		for(Label l : new Label[]{ errCreated, errLabel,errLabelFn,errLabelPh,errLabelPw,errLabelLog}) {
			l.setVisible(false);
		}
	}
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}	 


	private Owner cur = null;
	private boolean dirty = false; 
	
}


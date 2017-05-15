package com.capgemini.project.java.jfx.ui;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import com.capgemini.project.java.jfx.biz.Owner;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AuthentifController extends ControllerBase{
	@FXML
	private TableView<Owner> listOwners;
	@FXML private Button btnSign;
	@FXML private Button btnCancel;
	@FXML private TextField txtLabelLogin;
	@FXML private Label errLabelLogin;
	@FXML private PasswordField txtLabelPwd;
	@FXML private Label errLabelPwd;
	@FXML private Label errLabel;
	@FXML private Label sign;
	@FXML private Label welcomeLabel;
	@FXML private Label signLabel;
	
	@Override
	public void initialize(Mediator mediator) {
		
		try{
			EntityManager em = mediator.createEntityManager();
			log= (String) em.createQuery("SELECT o.login From Owner o where o.id=51").getSingleResult();
			mp=(String)em.createQuery("SELECT o.password From Owner o where o.id=51").getSingleResult();		
			
			
			em.close();
		}
		catch(PersistenceException e) {
			this.btnSign.setDisable(true);
			this.processPersistenceException(e);
		}
		
		handleBtnCancel(null);
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
		
		this.btnSign.setDisable(false);
	}
	@FXML
	private void handleBtnSign(ActionEvent event) throws IOException {
			
			boolean err=false;
			String loginUser=this.txtLabelLogin.getText();
			String passwHash = HashPassword(this.txtLabelPwd.getText());
			
			if(passwHash.equals(mp) && loginUser.equals(log)){  //To do faire comparaison par couple mp et log car une liste ici
				//Message de confirmation de connexion et ouverture d'une page
				this.welcomeLabel.setVisible(true);
				welcomeLabel.setText("Welcome to my personnal bank");
				this.signLabel.setVisible(false);
				
			}
			
			else{
				
				this.signLabel.setVisible(true);
				signLabel.setText("Sign in  to my personnal bank");
				this.errLabel.setVisible(true);
				errLabel.setText("Login or Password incorrect");
				this.welcomeLabel.setVisible(false);
			}

			if(this.txtLabelLogin.getText().isEmpty()){
				this.errLabelLogin.setVisible(true);
				err=true;
			}
			if(this.txtLabelPwd.getText().isEmpty()){
				this.errLabelPwd.setVisible(true);
				
				err=true;
			}	
			
			
		
			if(err) {
				//return false;
			}
		
	}
	
	@FXML
	private void handleBtnCancel(ActionEvent event) {
		this.txtLabelLogin.setText("");
		this.txtLabelPwd.setText("");
		this.errLabelPwd.setVisible(false);
		this.errLabelLogin.setVisible(false);
		this.welcomeLabel.setVisible(false);
		this.errLabel.setVisible(false);
		this.signLabel.setVisible(true);
		
	}
	//fonction de hachage
	public String HashPassword(String password) {
		byte[] uniqueKey = password.getBytes();
        byte[] hash      = null;

        try
        {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error("No MD5 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else
                hashString.append(hex.substring(hex.length() - 2));
        }
        
		return hashString.toString();
	}

	
	
	private void resetErrors() {
		for(Label l : new Label[]{errLabelPwd, errLabelLogin, errLabel}) {
			l.setVisible(false);
		}
	}
	private void processPersistenceException(PersistenceException e) {
		new Alert(AlertType.ERROR, "Database error : "+e.getLocalizedMessage(), ButtonType.OK).showAndWait();
	}	 


	
	private String log;
	private String mp;
	
}






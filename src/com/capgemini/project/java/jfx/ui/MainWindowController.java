package com.capgemini.project.java.jfx.ui;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

//import com.capgemini.training.java.jfx.biz.InvalidCsvFormatException;
//import com.capgemini.training.java.jfx.biz.TaskCollection;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindowController extends ControllerBase {

	@FXML private StackPane contentAccounts;
	@FXML private StackPane contentAdvisors;
	@FXML private StackPane contentOwner;
	@FXML private StackPane contentTargetTransactions;
	@FXML private StackPane contentTransactions;
	@FXML private StackPane contentAuthentification;
	
	@Override
	public void initialize(Mediator mediator) {
		try {
			contentAuthentification.getChildren().setAll(loadFxml("AuthentificationWindow.fxml"));
			contentAccounts.getChildren().setAll(loadFxml("AccountsOverview.fxml"));
			contentAdvisors.getChildren().setAll(loadFxml("AdvisorsOverview.fxml"));
			contentOwner.getChildren().setAll(loadFxml("OwnerView.fxml"));
			contentTargetTransactions.getChildren().setAll(loadFxml("TargetTransactionEdit.fxml"));
			contentTransactions.getChildren().setAll(loadFxml("TransactionsWindow.fxml"));
		}
		catch(IOException e) {
			// TODO alert
		}
	}
	
	/*@FXML
	private void handleMenuFileOpen(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		File choix = chooser.showOpenDialog(null);
		String err ="";
		
		if(choix!=null) {			
			try {
				// Charger le fichier
				FileReader fr = new FileReader(choix);
				try {
					//TaskCollection coll = TaskCollection.fromCsv(fr);
					
					try {
						// Charger le FXML (et son controleur)
						FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerView.fxml"));
						Node root = (Node)loader.load();
										
						content.getChildren().setAll(root); // Le mettre dans 'content'
					}
					catch(IOException e) {
						err = "Erreur lors de la préparation de l'affichage de la liste: "+e.getMessage();
					}
				}
				catch(IOException e) {
					err = "Erreur lors de la lecture du csv : "+e.getMessage();
				}
				catch(InvalidCsvFormatException e) {
					err = "Erreur de format csv : "+e.getMessage();
				}
				fr.close();
			}
			catch(IOException e) {
				err = "Erreur d'ouverture du csv : "+e.getMessage();
			}
			if(!err.isEmpty()) {
				new Alert(AlertType.ERROR, err).showAndWait();
			}
		}
	}*/
	/*@FXML
	private void handleMenuFileQuit(ActionEvent event) {
		Alert alert = new Alert(
				AlertType.CONFIRMATION,
				"Vous êtes sûr de vouloir quitter ?",
				ButtonType.OK,
				ButtonType.CANCEL
		);
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.isPresent() && result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}*/
}

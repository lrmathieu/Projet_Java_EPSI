package com.capgemini.project.java.jfx.ui;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.capgemini.project.java.jfx.ui.Mediator;
import com.capgemini.project.java.jfx.ui.ControllerBase;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private EntityManagerFactory emf;
	private Mediator mediator;
	
	@Override
	public void start(Stage primaryStage) {
<<<<<<< HEAD
		try {
			this.emf = Persistence.createEntityManagerFactory("Projet_Java_EPSI");
			this.mediator = new Mediator( this.emf );
			
			// fenetre Cedric (TransactionsWindow)
			//Scene scene = new Scene(ControllerBase.loadFxml("TransactionsWindow.fxml", mediator));
			// fenetres Raphael (AccountsOverview)
			//Scene scene = new Scene(ControllerBase.loadFxml("AccountsOverview.fxml", mediator));
			//Scene scene = new Scene(ControllerBase.loadFxml("AdvisorsOverview.fxml", mediator));
			// fenetre Bailo (OwnerView)
			//Scene scene = new Scene(ControllerBase.loadFxml("OwnerView.fxml", mediator));
=======
		try {		
			this.emf = Persistence.createEntityManagerFactory("Projet_Java_EPSI");
			this.mediator = new Mediator( this.emf );
			
			//Scene scene = new Scene(OwnerControllerBase.loadFxml("OwnerView.fxml", mediator));
			Scene scene = new Scene(ControllerBase.loadFxml("AccountsOverview.fxml", mediator));
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
>>>>>>> ajoutBanque
			
			//Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));     
		    //Scene scene = new Scene(root);
			
			Scene scene = new Scene(ControllerBase.loadFxml("MainWindow.fxml", mediator));
		
			scene.getStylesheets().add(getClass().getResource("MainWindow.fxml").toExternalForm());		
			primaryStage.setScene(scene);		
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		this.emf.close();
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package com.capgemini.project.java.jfx.ui;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.capgemini.project.java.jfx.ui.Mediator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private EntityManagerFactory emf;
	private Mediator mediator;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.emf = Persistence.createEntityManagerFactory("Projet_Java_EPSI");
			this.mediator = new Mediator( this.emf );
			
			Scene scene = new Scene(ControllerBase.loadFxml("TransactionsWindow.fxml", mediator));

			//Scene scene = new Scene(ControllerBase.loadFxml("AccountsOverview.fxml", mediator));
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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

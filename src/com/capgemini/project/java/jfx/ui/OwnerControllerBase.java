package com.capgemini.project.java.jfx.ui;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class OwnerControllerBase {
	private Mediator mediator = null;
	
	public abstract void initialize(Mediator mediator);
	
	public void initMediator(Mediator mediator) {
		if(mediator==null) {
			throw new NullPointerException("mediator cannot be null");
		}
		if(this.mediator!=null) {
			throw new UnsupportedOperationException("Cannot initMediator twice");
		}
		this.mediator = mediator;
		this.initialize(this.mediator);
	}
	public Mediator getMediator() {
		return this.mediator;
	}
	public Parent loadFxml(String fxml) throws IOException {
		return loadFxml(fxml, this.mediator);
	}
	public static Parent loadFxml(String fxml, Mediator mediator) throws IOException {
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxml));
		Parent root = (Parent)loader.load();
		OwnerControllerBase controller = loader.getController();
		
		controller.initMediator(mediator);
		return root;
	}
}
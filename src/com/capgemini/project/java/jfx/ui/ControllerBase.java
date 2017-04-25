package com.capgemini.project.java.jfx.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class ControllerBase {

	private Mediator m_mediator = null;
	
	public abstract void initialize(Mediator mediator);

	public void initMediator(Mediator mediator) {
		if (mediator==null) {
			throw new NullPointerException("mediator cannot be null !");
		}
		if (this.m_mediator!=null) {
			throw new UnsupportedOperationException("Cannot initMediator twice !");
		}
		this.m_mediator = mediator;
		this.initialize(this.m_mediator);
	}
	
	/*
	public void setMediator(Mediator mediator) {
		this.m_mediator = mediator;
	}
	*/
	
	public Mediator getMediator() {
		return this.m_mediator;
	}
	
	public Parent loadFxml(String fxml) throws IOException {
		return loadFxml(fxml, this.m_mediator);
	}
	
	public static Parent loadFxml(String fxml, Mediator mediator) throws IOException {
		FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxml));
		Parent root = (Parent)loader.load();
		ControllerBase controller = loader.getController();
		
		controller.initMediator(mediator);
		
		return root;
	}
	
}

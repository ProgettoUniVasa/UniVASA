package it.univaq.disim.ing.univasa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class LayoutController implements Initializable, DataInitializable<Utente> {

	@FXML
	private VBox menuBar;

	private Utente utente;

	private ViewDispatcher dispatcher;

	public LayoutController() {
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
	}

	@FXML
	public void esciAction(MouseEvent event) {
		dispatcher.logout();
	};

}

package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Prenotazione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class CambioModalitaVotazioneController implements Initializable, DataInitializable<Elettore> {
	@FXML
	private Button indietroButton;

	@FXML
	private Button allegaButton;

	@FXML
	private Button confermaButton;

	private ViewDispatcher dispatcher;
	
	private Elettore elettore;


	public CambioModalitaVotazioneController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@Override
	public void initializeData(Elettore elettore) {
		this.elettore = elettore;
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("elencoEventiPersonaliElettore", elettore);
	}
	

	@FXML
	public void allegaAction(ActionEvent event) {
		//allegare una immagine blob
	}
	

	@FXML
	public void confermaAction(ActionEvent event) {
		//l'amministratore deve riceve la richiesta di cambio modalita
		dispatcher.renderView("elencoEventiPersonaliElettore", elettore);
	}
}

package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class VotazioneOnlineController implements Initializable, DataInitializable<Evento> {
	private ViewDispatcher dispatcher;

	private EventoService eventoService;
	
	private Elettore elettore;

	public VotazioneOnlineController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}
	
	@Override
	public void initializeData(Evento evento) {
		try {
			this.elettore = elettore;
			List<Candidato> candidati = eventoService.visualizzaCandidati(evento);
			ObservableList<Candidato> candidatiData = FXCollections.observableArrayList(candidati);
			//candidatiGrid   ??????? .setItems(candidatiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("homeElettore", elettore);
	}
}

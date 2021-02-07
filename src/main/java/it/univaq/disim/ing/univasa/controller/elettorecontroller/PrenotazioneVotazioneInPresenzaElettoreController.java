package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.*;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Prenotazione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class PrenotazioneVotazioneInPresenzaElettoreController implements Initializable, DataInitializable<Prenotazione> { //<T> deve essere la prenotazione....

	@FXML
	private Button prenotatiInPresenzaButton;

	@FXML
	private Button annullaPrenotazioneInPresenzaButton;

	private ViewDispatcher dispatcher;

	private PrenotazioneService prenotazioneService;
	private EventoService eventoService;

	private Evento evento;
	private Prenotazione prenotazione;
	private Elettore elettore;

	public PrenotazioneVotazioneInPresenzaElettoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
		prenotazioneService = factory.getPrenotazioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Prenotazione prenotazione) {
		this.elettore = prenotazione.getElettore();
		this.evento = prenotazione.getEvento();
		this.prenotazione = prenotazione;
	}

	@FXML
	public void prenotatiInPresenzaAction(ActionEvent event) throws BusinessException {
		try {
			prenotazioneService.prenotazioneInSede(elettore, evento);
			dispatcher.renderView("elencoTuttiGliEventiElettore", elettore);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void annullaPrenotazioneInPresenzaAction(ActionEvent event){
		dispatcher.renderView("elencoTuttiGliEventiElettore", elettore);
	}
}

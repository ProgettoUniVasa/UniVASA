package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class PrenotazioneVotazioneInPresenzaElettoreController implements Initializable, DataInitializable<Evento> {

	@FXML
	private Button prenotatiInPresenzaButton;

	@FXML
	private Button annullaPrenotazioneInPresenzaButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Evento evento;

	private UtenteService utenteService;

	private Elettore elettore;

	public PrenotazioneVotazioneInPresenzaElettoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Evento evento) {
	}

	@FXML
	public void prenotatiInPresenzaAction(ActionEvent event) throws BusinessException {

	}

	@FXML
	public void annullaPrenotazioneInPresenzaAction(ActionEvent event) throws BusinessException {
		dispatcher.renderView("elencoTuttiGliEventiElettore", elettore);
	}
}
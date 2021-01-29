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

public class PrenotazioneVotazioneOnlineElettoreController implements Initializable, DataInitializable<Evento> {

	@FXML
	private Button prenotatiOnlineButton;

	@FXML
	private Button annullaPrenotazioneOnlineButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Evento evento;

	private UtenteService utenteService;

	private Elettore elettore;

	public PrenotazioneVotazioneOnlineElettoreController() {
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
	public void prenotatiOnlineAction(ActionEvent event) throws BusinessException {
		try {
			utenteService.prenotazioneOnline(elettore, evento);
			;

			dispatcher.renderView("elencoTuttiGliEventiElettore", elettore);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void annullaPrenotazioneOnlineAction(ActionEvent event) throws BusinessException {
		dispatcher.renderView("elencoTuttiGliEventiElettore", elettore);
	}
}

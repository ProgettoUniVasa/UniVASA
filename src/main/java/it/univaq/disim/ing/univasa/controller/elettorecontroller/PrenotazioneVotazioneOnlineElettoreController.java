package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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

public class PrenotazioneVotazioneOnlineElettoreController implements Initializable, DataInitializable<Prenotazione> {

	@FXML
	private Button prenotatiOnlineButton;
	
	@FXML
	private Button annullaPrenotazioneOnlineButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;
	private PrenotazioneService prenotazioneService;

	private Elettore elettore;
	private Prenotazione prenotazione;
	private Evento evento;

	public PrenotazioneVotazioneOnlineElettoreController() {
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
	public void prenotatiOnlineAction(ActionEvent event) throws BusinessException {
		try {
			prenotazioneService.prenotazioneOnline(elettore, evento);
			JOptionPane.showMessageDialog(null, "Prenotazione Online effettuata con successo!", " ",
					JOptionPane.INFORMATION_MESSAGE);
			dispatcher.renderView("elencoTuttiGliEventiElettore", elettore);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void annullaPrenotazioneOnlineAction(ActionEvent event){
		dispatcher.renderView("elencoTuttiGliEventiElettore", elettore);
	}
}

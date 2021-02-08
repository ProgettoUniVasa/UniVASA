package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.PrenotazioneService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Prenotazione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EliminaPrenotazioneController implements Initializable, DataInitializable<Prenotazione> {

	@FXML
	private TextField nomeEvento;

	@FXML
	private TextField nomeElettore;

	@FXML
	private TextField tipoPrenotazione;

	@FXML
	private Button eliminaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private PrenotazioneService prenotazioneService;

	private Evento evento;
	private Prenotazione prenotazione;
	private Elettore elettore;

	public EliminaPrenotazioneController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		prenotazioneService = factory.getPrenotazioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
		this.evento = prenotazione.getEvento();
		this.elettore = prenotazione.getElettore();
		this.nomeEvento.setText(evento.getNome());
		this.nomeElettore.setText(elettore.getNome());
		this.tipoPrenotazione.setText(String.valueOf(prenotazione.getTipoPrenotazione()));
	}

	@FXML
	public void eliminaAction(ActionEvent event) {
		try {
			prenotazioneService.eliminaPrenotazione(prenotazione);
			dispatcher.renderView("elencoEventiPersonaliElettore", elettore);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("elencoEventiPersonaliElettore", elettore);
	}
}
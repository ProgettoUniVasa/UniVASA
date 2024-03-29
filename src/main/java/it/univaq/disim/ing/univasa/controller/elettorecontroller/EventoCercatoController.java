package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
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

public class EventoCercatoController implements Initializable, DataInitializable<Prenotazione> {

	@FXML
	private TextField nome;

	@FXML
	private DatePicker dataInizio;

	@FXML
	private TextField oraInizio;

	@FXML
	private DatePicker dataFine;

	@FXML
	private TextField oraFine;

	@FXML
	private TextField luogo;

	@FXML
	private TextField regolamento;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Evento evento;
	private Prenotazione prenotazione;
	private Elettore elettore;

	private Long id;

	public EventoCercatoController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
		this.elettore = prenotazione.getElettore();
		this.evento = prenotazione.getEvento();
		this.nome.setText(evento.getNome());
		this.dataInizio.setValue(evento.getDataInizio());
		this.oraInizio.setText(evento.getOraInizio());
		this.dataFine.setValue(evento.getDataFine());
		this.oraFine.setText(evento.getOraFine());
		this.luogo.setText(evento.getLuogo());
		this.regolamento.setText(evento.getRegolamento());

		nome.setEditable(false);
		regolamento.setEditable(false);
		dataInizio.setEditable(false);
		dataFine.setEditable(false);
		luogo.setEditable(false);
		oraFine.setEditable(false);
		oraInizio.setEditable(false);

	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("elencoTuttiGliEventiElettore", elettore);
	}

}

package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
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

public class ReportEventoController implements Initializable, DataInitializable<Prenotazione> {

	@FXML
	private TextField nomeEvento;

	@FXML
	private TextField regolamentoEvento;

	@FXML
	private DatePicker dataInizio;

	@FXML
	private DatePicker dataFine;

	@FXML
	private TextField luogo;

	@FXML
	private TextField report_statistiche;

	@FXML
	private TextField report_risultati;

	private ViewDispatcher dispatcher;

	private Evento evento;
	private Prenotazione prenotazione;
	private Elettore elettore;

	public ReportEventoController() {
		dispatcher = ViewDispatcher.getInstance();
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
		this.regolamentoEvento.setText(evento.getRegolamento());
		this.dataInizio.setValue(evento.getDataInizio());
		this.dataFine.setValue(evento.getDataFine());
		this.luogo.setText(evento.getLuogo());
		this.report_statistiche.setText(evento.getReport_statistiche());
		this.report_risultati.setText(evento.getReport_risultati());

		nomeEvento.setEditable(false);
		regolamentoEvento.setEditable(false);
		dataInizio.setEditable(false);
		dataFine.setEditable(false);
		luogo.setEditable(false);
		report_risultati.setEditable(false);
		report_statistiche.setEditable(false);


	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("elencoReport", elettore);
	}
}
package it.univaq.disim.ing.univasa.controller.elettorecontroller;

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
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegolamentoEventoInCorsoController implements Initializable, DataInitializable<Prenotazione> {

	@FXML
	private TextField nome;

	@FXML
	private TextField regolamento;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Evento evento;
	private Prenotazione prenotazione;
	private Elettore elettore;

	public RegolamentoEventoInCorsoController() {
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
		this.regolamento.setText("E' possibile votare " + evento.getNumero_preferenze_esprimibili() + " candidato/i per scheda elettorale. \n " + evento.getRegolamento());
		this.nome.setEditable(false);
		this.regolamento.setEditable(false);
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("elencoEventiInCorso", elettore);
	}
}

package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Turnazione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AggiungiVotiOperatoreController implements Initializable, DataInitializable<Candidato> {

	@FXML
	private Label nome;

	@FXML
	private Label cognome;

	@FXML
	private Label email;

	@FXML
	private Label nomeEvento;

	@FXML
	private TextField voti;

	@FXML
	private Button confermaButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Candidato candidato;

	private Evento evento;

	public AggiungiVotiOperatoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void initializeData(Candidato candidato) {
		this.candidato = candidato;
		this.evento = candidato.getEvento();
		confermaButton.disableProperty().bind((voti.textProperty().isEmpty()));

		nome.setText(candidato.getNome());
		cognome.setText(candidato.getCognome());
		email.setText(candidato.getEmail());
		nomeEvento.setText(candidato.getEvento().getNome());

	}

	@FXML
	public void confermaAction(ActionEvent event) {
		try {
			Turnazione turnazione = new Turnazione();
			turnazione.setEvento(evento);
			eventoService.caricaRisultatiInPresenza(candidato, Integer.valueOf(voti.getText()));
			dispatcher.renderView("caricaVotiOperatore", turnazione);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		Turnazione turnazione = new Turnazione();
		turnazione.setEvento(evento);
		dispatcher.renderView("caricaVotiOperatore", turnazione);
	}
}
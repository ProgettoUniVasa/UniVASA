package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import it.univaq.disim.ing.univasa.business.*;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.*;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

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
		confermaButton.disableProperty()
				.bind((voti.textProperty().isEmpty()));

		nome.setText(candidato.getNome());
		cognome.setText(candidato.getCognome());
		email.setText(candidato.getEmail());
		nomeEvento.setText(candidato.getEvento().getNome());

	}

	@FXML
	public void confermaAction(ActionEvent event) {
		try {
			eventoService.caricaRisultatiInPresenza(candidato, Integer.valueOf(voti.getText()));
			dispatcher.renderView("caricaVotiOperatore", evento);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("eventoOperatore", evento);
	}
}
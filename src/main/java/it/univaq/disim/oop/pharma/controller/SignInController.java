package it.univaq.disim.oop.pharma.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.UtenteService;
import it.univaq.disim.oop.pharma.domain.Medico;
import it.univaq.disim.oop.pharma.domain.Paziente;
import it.univaq.disim.oop.pharma.domain.Utente;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class SignInController implements Initializable, DataInitializable<Utente> {

	@FXML
	private Button creaMedicoButton;

	@FXML
	private Button creaPazienteButton;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	public SignInController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Utente utente) {
	}

	@FXML
	public void creaMedicoAction(ActionEvent event) {
		Medico medico = new Medico();
		dispatcher.registraMedico(medico);
	}

	@FXML
	public void creaPazienteAction(ActionEvent event) {
		Paziente paziente = new Paziente();
		dispatcher.registraPaziente(paziente);
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.logout();
	}

}
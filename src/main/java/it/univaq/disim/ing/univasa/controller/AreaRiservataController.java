package it.univaq.disim.ing.univasa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.domain.Utente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AreaRiservataController implements Initializable, DataInitializable<Utente> {

	@FXML
	private Label nomeLabel;

	@FXML
	private Label cognomeLabel;

	@FXML
	private Label cfLabel;

	@FXML
	private Label dataNascitaLabel;

	@FXML
	private Label luogoNascitaLabel;

	@FXML
	private Label residenzaLabel;

	@FXML
	private Label telefonoLabel;

	@FXML
	private Label usernameLabel;

	@FXML
	private Label passwordLabel;

	public AreaRiservataController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Utente utente) {

		this.nomeLabel.setText(utente.getNome());
		this.cognomeLabel.setText(utente.getCognome());
		this.cfLabel.setText(utente.getCf());
		this.dataNascitaLabel.setText("" + utente.getDataNascita());
		this.luogoNascitaLabel.setText(utente.getLuogoNascita());
		this.residenzaLabel.setText(utente.getResidenza());
		this.telefonoLabel.setText(utente.getTelefono());
		this.usernameLabel.setText(utente.getUsername());
		this.passwordLabel.setText(utente.getPassword());

	}

}

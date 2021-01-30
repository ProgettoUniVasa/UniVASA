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
	private Label emailLabel;

	@FXML
	private Label data_nascitaLabel;

	@FXML
	private Label telefonoLabel;

	@FXML
	private Label nome_universitàLabel;

	@FXML
	private Label dipartimentoLabel;

	@FXML
	private Label usernameLabel;

	public AreaRiservataController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Utente utente) {

		this.nomeLabel.setText(utente.getNome());
		this.cognomeLabel.setText(utente.getCognome());
		this.emailLabel.setText(utente.getEmail());
		this.data_nascitaLabel.setText("" + utente.getData_nascita());
		this.telefonoLabel.setText(utente.getTelefono());
		this.nome_universitàLabel.setText(utente.getNome_università());
		this.dipartimentoLabel.setText(utente.getDipartimento());
		this.usernameLabel.setText(utente.getUsername());

	}

}

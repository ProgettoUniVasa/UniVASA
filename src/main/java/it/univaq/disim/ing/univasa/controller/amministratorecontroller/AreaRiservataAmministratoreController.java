package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AreaRiservataAmministratoreController implements Initializable, DataInitializable<Amministratore> {

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

	private ViewDispatcher dispatcher;

	private Amministratore amministratore;

	public AreaRiservataAmministratoreController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Amministratore amministratore) {
		this.nomeLabel.setText(amministratore.getNome());
		this.cognomeLabel.setText(amministratore.getCognome());
		this.emailLabel.setText(amministratore.getEmail());
		this.data_nascitaLabel.setText("" + amministratore.getData_nascita());
		this.telefonoLabel.setText(amministratore.getTelefono());
		this.nome_universitàLabel.setText(amministratore.getNome_università());
		this.dipartimentoLabel.setText(amministratore.getDipartimento());
		this.usernameLabel.setText(amministratore.getUsername());
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("dashboardAmministratore", amministratore);
	}

}

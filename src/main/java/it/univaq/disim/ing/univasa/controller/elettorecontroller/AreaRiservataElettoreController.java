package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AreaRiservataElettoreController implements Initializable, DataInitializable<Elettore> {
	
	private ViewDispatcher dispatcher;
	private Elettore elettore;

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
	private Label matricolaLabel;

	@FXML
	private Label usernameLabel;

	public AreaRiservataElettoreController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Elettore elettore) {
		this.elettore = elettore;
		this.nomeLabel.setText(elettore.getNome());
		this.cognomeLabel.setText(elettore.getCognome());
		this.emailLabel.setText(elettore.getEmail());
		this.data_nascitaLabel.setText("" + elettore.getData_nascita());
		this.telefonoLabel.setText(elettore.getTelefono());
		this.nome_universitàLabel.setText(elettore.getNome_università());
		this.dipartimentoLabel.setText(elettore.getDipartimento());
		this.matricolaLabel.setText(elettore.getMatricola());
		
		this.usernameLabel.setText(elettore.getUsername());

	}
	
	@FXML
	public void indietroAction(ActionEvent event) {
			dispatcher.renderView("homeElettore", elettore);
	}


}

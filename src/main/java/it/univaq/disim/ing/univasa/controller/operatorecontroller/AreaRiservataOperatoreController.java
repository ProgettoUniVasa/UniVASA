package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AreaRiservataOperatoreController implements Initializable, DataInitializable<Operatore> {
	
	private ViewDispatcher dispatcher;
	private Operatore operatore;
	private UtenteService utenteService;

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


	public AreaRiservataOperatoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Operatore operatore) {
		this.operatore = operatore;

		this.nomeLabel.setText(operatore.getNome());
		this.cognomeLabel.setText(operatore.getCognome());
		this.emailLabel.setText(operatore.getEmail());
		this.data_nascitaLabel.setText("" + operatore.getData_nascita());
		this.telefonoLabel.setText(operatore.getTelefono());
		this.nome_universitàLabel.setText(operatore.getNome_università());
		this.dipartimentoLabel.setText(operatore.getDipartimento());
		this.usernameLabel.setText(operatore.getUsername());

	}
	
	@FXML
	public void indietroAction(ActionEvent event) {
			dispatcher.renderView("dashboardOperatore", operatore);
	}

}

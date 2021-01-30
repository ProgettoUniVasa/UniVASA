package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class DashboardAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private Button eventiButton;

	@FXML
	private Button turniButton;

	@FXML
	private Button operatoriButton;

	@FXML
	private Button reportButton;

	@FXML
	private Button amministratoriButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Amministratore amministratore;

	public DashboardAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Amministratore amministratore) {
	}

	@FXML
	public void mostraEventiAction(ActionEvent event) {
		dispatcher.renderView("listaEventiAmministratore", amministratore);
	}

	@FXML
	public void associazioneTurniAction(ActionEvent event) {
		dispatcher.renderView("listaTurnazioniAmministratore", amministratore);
	}

	@FXML
	public void creazioneReportAction(ActionEvent event) {
		dispatcher.renderView("listaReportAmministratore", amministratore);
	}

	@FXML
	public void gestioneOperatoriAction(ActionEvent event) {
		dispatcher.renderView("gestioneOperatoriAmministratore", amministratore);
	}

	@FXML
	public void gestioneAmministratoriAction(ActionEvent event) {
		dispatcher.renderView("gestioneAmministratoriAmministratore", amministratore);
	}

	@FXML
	public void esciAction(ActionEvent event) {
		dispatcher.logout();
	}

	@FXML
	public void visualizzaProfiloAction(MouseEvent event) {
		dispatcher.renderView("areaRiservata", amministratore);
	};

}
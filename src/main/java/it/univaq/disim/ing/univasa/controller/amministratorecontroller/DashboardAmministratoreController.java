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

public class DashboardAmministratoreController implements Initializable, DataInitializable<Utente> {

	@FXML
	private Button eventiButton;
	
	@FXML
	private Button turniButton;

	@FXML
	private Button reportButton;

	@FXML
	private Button esciButton;
	
	@FXML
	private Button profiloButton;

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
	public void initializeData(Utente utente) {
	}

	@FXML
	public void mostraEventiAction(ActionEvent event) {
		dispatcher.renderView("listaEventiAmministratore", amministratore);
	}

	//dio porco le turnazioni.
	@FXML
	public void associazioneTurni(ActionEvent event) {
		dispatcher.renderView("li mortacci tua.", amministratore);
	}
	
	@FXML
	public void creazioneReportAction(ActionEvent event) {
		dispatcher.renderView("listaReportAmministratore", amministratore);
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
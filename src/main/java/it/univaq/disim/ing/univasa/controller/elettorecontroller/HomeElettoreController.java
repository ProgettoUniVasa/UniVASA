package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class HomeElettoreController implements Initializable, DataInitializable<Elettore> {

	private ViewDispatcher dispatcher;
	private Elettore elettore;
	private UtenteService utenteService;

	public HomeElettoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initializeData(Elettore elettore) {
		this.elettore = elettore;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void esciAction(MouseEvent event) {
		dispatcher.logout();
	};

	@FXML
	public void visualizzaEventiAction(ActionEvent event) {
		dispatcher.renderView("elencoTuttiGliEventiElettore", elettore);
	}

	@FXML
	public void visualizzaEventiPersonaliAction(ActionEvent event) {
		dispatcher.renderView("elencoEventiPersonaliElettore", elettore);
	}

	@FXML
	public void votaAction(ActionEvent event) {
		dispatcher.renderView("elencoEventiInCorso", elettore);
	}

	@FXML
	public void visualizzaReportAction(ActionEvent event) {
		dispatcher.renderView("elencoReport", elettore);
	}

}

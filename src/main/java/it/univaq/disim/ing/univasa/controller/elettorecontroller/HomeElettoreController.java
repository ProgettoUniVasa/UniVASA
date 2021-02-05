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

public class HomeElettoreController implements Initializable, DataInitializable<Elettore> { //DataInitializable<Elettore>?
	
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
	public void visualizzaProfiloAction(MouseEvent event) {
		dispatcher.renderView("areaRiservataElettore", elettore);
	}

	@FXML
	public void visualizzaEventiAction(ActionEvent event) {
		dispatcher.renderView("elencoTuttiGliEventiElettore", elettore); //potrebbe andare bene un metodo generale in dispatcher 
																		 //per tutti gli utenti tranne l'admin ("dispatcher.elencoEventi(Utente-)" piuttosto che renderView
	}
	
	@FXML
	public void visualizzaEventiPersonaliAction(ActionEvent event) {
		dispatcher.renderView("elencoEventiPersonaliElettore", elettore); //elencoEventiPersonaliElettore.fxml; nella tableView ci sarà la colonna di bottoni "azione per cambiare modalità" 
																		  //che avrà eccezione in caso di stato.prenotazione_online, e porterà a "CambioModalitaVotazione" in caso di prenotazione in sede 
	}
	
	@FXML
	public void votaAction(ActionEvent event) {
		dispatcher.renderView("votazioneOnline", elettore); // tableView con evento che è votabile ORA e bottone per votare che porta a "schermataVotazione"
	}

	@FXML
	public void visualizzaReportAction(ActionEvent event) {
		dispatcher.renderView("elencoReport", elettore); //elencoReport.fxml
	}												 // tableView con evento (tutti anche quelli a cui l'elettore non si è prenotato? in tal caso
													     // basta un metodo generico?) e bottone Visualizza per vedere i report del relativo evento.
}															  


															  //3 opzioni: l'elettore vedrà gli eventi che può votare nel momento in cui clicca il bottone, 
															  //quindi una lista di eventi da cui sceglierà quello in cui votare "online" ora ,
															  //che successivamente, tramite un bottone, 
															  //porta alla schermata della scheda elettorale online. In alternativa si può usare 
		 													  //questo bottone per vedere gli Eventi Attivi Oggi, ma sarebbe ridondante. Oppure per 
															  //vedere i soli risultati degli eventi terminati. 

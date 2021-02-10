package it.univaq.disim.ing.univasa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class LayoutController implements Initializable, DataInitializable<Utente> {

	private static final MenuElement[] MENU_AMMINISTRATORE = {
			new MenuElement("Lista Eventi", "listaEventiAmministratore"),
			new MenuElement("Turni Operatori", "listaTurnazioniAmministratore"),
			new MenuElement("Crea Report", "listaReportAmministratore"),
			new MenuElement("Gestione operatori", "gestioneOperatoriAmministratore"),
			new MenuElement("Gestione segreteria", "gestioneAmministratoriAmministratore"),
			new MenuElement("Area Riservata", "areaRiservataAmministratore") };

	private static final MenuElement MENU_HOME_AMMINISTRATORE = new MenuElement("Home", "dashboardAmministratore");

	private static final MenuElement MENU_HOME_OPERATORE = new MenuElement("Home", "dashboardOperatore");

	private static final MenuElement MENU_HOME_ELETTORE = new MenuElement("Home", "homeElettore");

	private static final MenuElement[] MENU_OPERATORE = { new MenuElement("Lista eventi", "listaEventiOperatore"),
			new MenuElement("Lista turni", "lavoroOperatore"),
			new MenuElement("Area Riservata", "areaRiservataOperatore") };

	private static final MenuElement[] MENU_ELETTORE = { new MenuElement("Eventi in corso", "elencoEventiInCorso"),
			new MenuElement("Tutti gli eventi", "elencoTuttiGLiEventiElettore"),
			new MenuElement("Eventi prenotati", "elencoEventiPersonaliElettore"),
			new MenuElement("Risultati e statistiche", "elencoReport"),
			new MenuElement("Area Riservata", "areaRiservataElettore") };
	@FXML
	private VBox menuBar;

	private Utente utente;

	private ViewDispatcher dispatcher;

	public LayoutController() {
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;

		if (utente instanceof Amministratore) {
			menuBar.getChildren().addAll(createButton(MENU_HOME_AMMINISTRATORE));
			menuBar.getChildren().add(new Separator());
			for (MenuElement menu : MENU_AMMINISTRATORE) {
				menuBar.getChildren().add(createButton(menu));
			}
		}

		if (utente instanceof Operatore) {
			menuBar.getChildren().addAll(createButton(MENU_HOME_OPERATORE));
			menuBar.getChildren().add(new Separator());
			for (MenuElement menu : MENU_OPERATORE) {
				menuBar.getChildren().add(createButton(menu));
			}
		}

		if (utente instanceof Elettore) {
			menuBar.getChildren().addAll(createButton(MENU_HOME_ELETTORE));
			menuBar.getChildren().add(new Separator());
			for (MenuElement menu : MENU_ELETTORE) {
				menuBar.getChildren().add(createButton(menu));
			}
		}
	}

	@FXML
	public void esciAction(MouseEvent event) {
		dispatcher.logout();
	}

	private Button createButton(MenuElement viewItem) {
		Button button = new Button(viewItem.getNome());
		button.setStyle("-fx-background-color: transparent; -fx-font-size: 14;");
		button.setTextFill(Paint.valueOf("white"));
		button.setPrefHeight(20);
		button.setPrefWidth(180);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dispatcher.renderView(viewItem.getVista(), utente);
			}
		});
		return button;
	}

}

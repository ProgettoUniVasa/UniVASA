package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.PrenotazioneService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Prenotazione;
import it.univaq.disim.ing.univasa.domain.Stato;
import it.univaq.disim.ing.univasa.domain.StatoEvento;
import it.univaq.disim.ing.univasa.domain.TipoPrenotazione;
import it.univaq.disim.ing.univasa.domain.Utente;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ElencoEventiPersonaliElettoreController implements Initializable, DataInitializable<Elettore> {
	@FXML
	private TableView<Prenotazione> eventiPersonaliTable;

	@FXML
	private TableColumn<Prenotazione, String> nomeTableColumn;

	@FXML
	private TableColumn<Prenotazione, LocalDate> dataInizioTableColumn;

	@FXML
	private TableColumn<Prenotazione, LocalDate> dataFineTableColumn;

	@FXML
	private TableColumn<Prenotazione, String> oraInizioTableColumn;

	@FXML
	private TableColumn<Prenotazione, String> oraFineTableColumn;

	@FXML
	private TableColumn<Prenotazione, String> luogoTableColumn;

	@FXML
	private TableColumn<Prenotazione, Button> regolamentoTableColumn;

	@FXML
	private TableColumn<Prenotazione, StatoEvento> statoEventoTableColumn; // uso String per visualizzare la mia modalità di
																		// voto (in
	// presenza / online) se la prenotazione è stata effettuata.
	// Se sono in presenza, devo poter cliccare il tasto per
	// cambiare modalità di voto ed accedere alla vista che
	// permette il cambio modalità di voto.
	// Se l'evento è finito, devo poter visualizzare le
	// statistiche e i risultati dell'evento tramite una nuova
	// vista.
	// Il problema è: sono tutte stringhe oppure c'è qualche
	// bottone?
	@FXML
	private TableColumn<Prenotazione, TipoPrenotazione> prenotazioneTableColumn; // come prendo la prenotazione
																					// relativa?

	@FXML
	private TableColumn<Prenotazione, Button> azioneTableColumn;

	@FXML
	private Button esciButton;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService; // vedere service
	
	private PrenotazioneService prenotazioneService;
	
	private Elettore elettore;

	public ElencoEventiPersonaliElettoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance(); // vedere business factory
		eventoService = factory.getEventoService();
		prenotazioneService = factory.getPrenotazioneService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		dataInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
		dataFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
		oraInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("oraInizio"));
		oraFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("oraFine"));
		luogoTableColumn.setCellValueFactory(new PropertyValueFactory<>("luogo"));

		regolamentoTableColumn.setStyle("-fx-alignment: CENTER;");
		regolamentoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Prenotazione, Button> param) {
						final Button regolamentoButton = new Button("Visualizza");
						regolamentoButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("regolamentoEventoPersonale", param.getValue()); // creare vista
								// regolamentoEvento
							}
						});
						return new SimpleObjectProperty<Button>(regolamentoButton);
					}
				});

		statoEventoTableColumn.setStyle("-fx-alignment: CENTER;");
		statoEventoTableColumn.setCellValueFactory(new PropertyValueFactory<>("statoEvento")); // non esiste come
																								// attributo di
		// Evento, si potrebbe mettere uno
		// stato votazione/stato evento?
		prenotazioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("tipoPrenotazione"));
		azioneTableColumn.setStyle("-fx-alignment: CENTER;");
		azioneTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Prenotazione, Button> param) {
						final Button azioneButton = new Button("Modifica");
						azioneButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								Prenotazione prenotazione = new Prenotazione();
								Evento evento = prenotazione.getEvento();
								if (prenotazione.getTipoPrenotazione().equals(TipoPrenotazione.IN_PRESENZA)
										&& !(evento.getStatoEvento().equals(StatoEvento.TERMINATO))) {
									dispatcher.renderView("cambioModalitaVotazione", param.getValue()); 
								}
								else ; //gestire eccezione quando clicco modifica e non potrei <----------------------------------------------------
							}
						});
						return new SimpleObjectProperty<Button>(azioneButton);
					}
				});
		/*
		 * statoEventoTableColumn.setCellFactory(new Callback<TableColumn<Evento,
		 * String>, TableCell<Evento, String>>() { public TableCell<Evento, String>
		 * call(TableColumn<Evento, String> param) { return new TableCell<Evento,
		 * String>() {
		 * 
		 * @Override public void updateItem(String item, boolean empty) {
		 * super.updateItem(item, empty); if (!isEmpty()) { if
		 * (item.contains(StatoEvento.IN_CORSO.toString())) { this.setText("in corso");
		 * this.setTextFill(Color.GREEN); } else if
		 * (item.contains(StatoEvento.PROGRAMMATO.toString())) {
		 * this.setText("programmato"); this.setTextFill(Color.ORANGE); } else if
		 * (item.contains(StatoEvento.TERMINATO.toString())) {
		 * this.setText("Evento terminato"); this.setTextFill(Color.RED); }
		 * setText(item);
		 * 
		 * } } }; } });
		 */

	}

	@Override
	public void initializeData(Elettore elettore) {
		try {
			this.elettore = elettore;
			List<Prenotazione> prenotazioni = prenotazioneService.trovaPrenotazioniElettore(elettore); 
			ObservableList<Prenotazione> prenotazioniData = FXCollections.observableArrayList(prenotazioni);
			eventiPersonaliTable.setItems(prenotazioniData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("homeElettore", elettore);
	}

}

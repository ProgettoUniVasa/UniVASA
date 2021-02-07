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
	private TableColumn<Prenotazione, String> luogoTableColumn;

	@FXML
	private TableColumn<Prenotazione, Button> regolamentoTableColumn;

	@FXML
	private TableColumn<Prenotazione, StatoEvento> statoEventoTableColumn; // uso String per visualizzare la mia
																			// modalità di
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

	private ViewDispatcher dispatcher;

	private PrenotazioneService prenotazioneService;

	private Elettore elettore;

	public ElencoEventiPersonaliElettoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance(); // vedere business factory
		prenotazioneService = factory.getPrenotazioneService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prenotazione, String> param) {
						return new SimpleObjectProperty<String>(param.getValue().getEvento().getNome());
					}
				});
		dataInizioTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, LocalDate>, ObservableValue<LocalDate>>() {
					@Override
					public ObservableValue<LocalDate> call(CellDataFeatures<Prenotazione, LocalDate> param) {
						return new SimpleObjectProperty<LocalDate>(param.getValue().getEvento().getDataInizio());
					}
				});
		dataFineTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, LocalDate>, ObservableValue<LocalDate>>() {
					@Override
					public ObservableValue<LocalDate> call(CellDataFeatures<Prenotazione, LocalDate> param) {
						return new SimpleObjectProperty<LocalDate>(param.getValue().getEvento().getDataFine());
					}
				});
		luogoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prenotazione, String> param) {
						return new SimpleObjectProperty<String>(param.getValue().getEvento().getLuogo());
					}
				});
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
		statoEventoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, StatoEvento>, ObservableValue<StatoEvento>>() {
					@Override
					public ObservableValue<StatoEvento> call(CellDataFeatures<Prenotazione, StatoEvento> param) {
						return new SimpleObjectProperty<StatoEvento>(param.getValue().getEvento().getStatoEvento());
					}
				});
		prenotazioneTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, TipoPrenotazione>, ObservableValue<TipoPrenotazione>>() {
					@Override
					public ObservableValue<TipoPrenotazione> call(
							CellDataFeatures<Prenotazione, TipoPrenotazione> param) {
						return new SimpleObjectProperty<TipoPrenotazione>(param.getValue().getTipoPrenotazione());
					}
				});
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
								if (prenotazione.getTipoPrenotazione().equals(TipoPrenotazione.in_presenza)
										&& !(evento.getStatoEvento().equals(StatoEvento.terminato))) {
									dispatcher.renderView("cambioModalitaVotazione", param.getValue());
								} else
									; // gestire eccezione quando clicco modifica e non potrei
										// <----------------------------------------------------
							}
						});
						return new SimpleObjectProperty<Button>(azioneButton);
					}
				});
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

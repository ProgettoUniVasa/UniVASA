package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.PrenotazioneService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.*;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ElencoEventiInCorsoController implements Initializable, DataInitializable<Elettore> {

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
	private TableColumn<Prenotazione, Button> azioneTableColumn;

	@FXML
	private Button esciButton;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private PrenotazioneService prenotazioneService;
	private Elettore elettore;

	public ElencoEventiInCorsoController() {
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
						return new SimpleStringProperty(param.getValue().getEvento().getNome());
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
		oraInizioTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prenotazione, String> param) {
						return new SimpleStringProperty(param.getValue().getEvento().getOraInizio());
					}
				});
		oraFineTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prenotazione, String> param) {
						return new SimpleStringProperty(param.getValue().getEvento().getOraFine());
					}
				});
		luogoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prenotazione, String> param) {
						return new SimpleStringProperty(param.getValue().getEvento().getLuogo());
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
								dispatcher.renderView("regolamentoEventoPersonale", param.getValue().getEvento()); // creare vista
								// regolamentoEvento
							}
						});
						return new SimpleObjectProperty<Button>(regolamentoButton);
					}
				});

		azioneTableColumn.setStyle("-fx-alignment: CENTER;");
		azioneTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prenotazione, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Prenotazione, Button> param) {
						final Button azioneButton = new Button("Vota");
						azioneButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								Prenotazione prenotazione = new Prenotazione();
								Evento evento = prenotazione.getEvento();
								if (prenotazione.getTipoPrenotazione().equals(TipoPrenotazione.online)
										&& (evento.getStatoEvento().equals(StatoEvento.in_corso))) {
									dispatcher.renderView("votazioneOnline", param.getValue());
								} else
									; // gestire eccezione quando clicco vota e non potrei
										// <----------------------------------------------------
							}
						});
						return new SimpleObjectProperty<Button>(azioneButton);
					}
				});
	}

	@Override
	public void initializeData(Elettore elettore) {
		this.elettore = elettore;
		List<Prenotazione> prenotazioni = prenotazioneService.trovaPrenotazioniOnlineInCorso(elettore);
		ObservableList<Prenotazione> prenotazioniData = FXCollections.observableArrayList(prenotazioni);
		eventiPersonaliTable.setItems(prenotazioniData);
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("homeElettore", elettore);
	}

}

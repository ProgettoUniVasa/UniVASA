package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Prenotazione;
import it.univaq.disim.ing.univasa.domain.StatoEvento;
import it.univaq.disim.ing.univasa.domain.TipoPrenotazione;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ElencoEventiInCorsoController implements Initializable, DataInitializable<Elettore> {

	@FXML
	private TableView<Evento> eventiPersonaliTable;

	@FXML
	private TableColumn<Evento, String> nomeTableColumn;

	@FXML
	private TableColumn<Evento, LocalDate> dataInizioTableColumn;

	@FXML
	private TableColumn<Evento, LocalDate> dataFineTableColumn;

	@FXML
	private TableColumn<Evento, String> oraInizioTableColumn;

	@FXML
	private TableColumn<Evento, String> oraFineTableColumn;

	@FXML
	private TableColumn<Evento, String> luogoTableColumn;

	@FXML
	private TableColumn<Evento, Button> regolamentoTableColumn;

	@FXML
	private TableColumn<Prenotazione, TipoPrenotazione> prenotazioneTableColumn;

	@FXML
	private TableColumn<Evento, Button> azioneTableColumn;

	@FXML
	private Button esciButton;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService; // vedere service

	private Elettore elettore;

	public ElencoEventiInCorsoController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance(); // vedere business factory
		eventoService = factory.getEventoService();
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
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
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

		prenotazioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("tipoPrenotazione"));
		azioneTableColumn.setStyle("-fx-alignment: CENTER;");
		azioneTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button azioneButton = new Button("Vota");
						azioneButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								Prenotazione prenotazione = new Prenotazione();
								Evento evento = prenotazione.getEvento();
								if (prenotazione.getTipoPrenotazione().equals(TipoPrenotazione.ONLINE)
										&& (evento.getStatoEvento().equals(StatoEvento.IN_CORSO))) {
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
		try {
			this.elettore = elettore;
			List<Evento> eventi = eventoService.trovaEventiPrenotatiInCorsoElettore(elettore); // vedere service
			ObservableList<Evento> eventiData = FXCollections.observableArrayList(eventi);
			List<Prenotazione> prenotazioni = eventoService.trovaPrenotazioniElettore(elettore, elettore.getEvento()); // non
																														// so
																														// come
																														// far
																														// vedere
																														// le
																														// prenotazioni
																														// :)
			ObservableList<Prenotazione> prenotazioniData = FXCollections.observableArrayList(prenotazioni);
			eventiPersonaliTable.setItems(eventiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("homeElettore", elettore);
	}

}

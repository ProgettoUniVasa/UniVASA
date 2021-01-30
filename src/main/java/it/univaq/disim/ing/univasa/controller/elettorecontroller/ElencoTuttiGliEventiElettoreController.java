package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ElencoTuttiGliEventiElettoreController implements Initializable, DataInitializable<Elettore> {

	@FXML
	private TableView<Evento> tuttiGliEventiTable;

	@FXML
	private TableColumn<Evento, String> nomeTableColumn;

	@FXML
	private TableColumn<Evento, LocalDateTime> dataOraInizioTableColumn;

	@FXML
	private TableColumn<Evento, LocalDateTime> dataOraFineTableColumn;

	@FXML
	private TableColumn<Evento, String> luogoTableColumn;

	@FXML
	private TableColumn<Evento, Button> regolamentoTableColumn;

	@FXML
	private TableColumn<Evento, Button> prenotazioneInPresenzaTableColumn;

	@FXML
	private TableColumn<Evento, Button> prenotazioneOnlineTableColumn;
	
	@FXML
	private Button indietroButton;

	@FXML
	private Button esciButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Elettore elettore;

	public ElencoTuttiGliEventiElettoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		dataOraInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataOraInizio"));
		dataOraFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataOraFine"));
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
								dispatcher.renderView("regolamentoTuttiGliEventi", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(regolamentoButton);
					}
				});

		prenotazioneInPresenzaTableColumn.setStyle("-fx-alignment: CENTER;");
		prenotazioneInPresenzaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button prenotazioneInPresenzaButton = new Button("Prenota");
						prenotazioneInPresenzaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("prenotazioneVotazioneInPresenza", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(prenotazioneInPresenzaButton);
					}
				});

		prenotazioneOnlineTableColumn.setStyle("-fx-alignment: CENTER;");
		prenotazioneOnlineTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button prenotazioneOnlineButton = new Button("Prenota");
						prenotazioneOnlineButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("prenotazioneVotazioneOnline", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(prenotazioneOnlineButton);
					}
				});

	}

	@Override
	public void initializeData(Elettore elettore) {
		try {
			this.elettore = elettore;
			List<Evento> eventi = eventoService.trovaEventiDaPrenotare(elettore);
			ObservableList<Evento> eventiData = FXCollections.observableArrayList(eventi);
			tuttiGliEventiTable.setItems(eventiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void indietroAction(MouseEvent event) {
		dispatcher.renderView("homeElettore", elettore);
	}
	
	@FXML
	public void esciAction(MouseEvent event) {
		dispatcher.logout();
	};

}
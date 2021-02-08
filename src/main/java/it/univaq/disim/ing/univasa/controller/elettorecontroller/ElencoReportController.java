package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
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
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ElencoReportController implements Initializable, DataInitializable<Elettore> {
	
	@FXML
	private TableView<Evento> eventiTerminatiTable;
	
	@FXML
	private TableColumn<Evento, String> nomeTableColumn;
	
	@FXML
	private TableColumn<Evento, String> dataOraInizioTableColumn;
	
	@FXML
	private TableColumn<Evento, String> dataOraFineTableColumn;
	
	@FXML
	private TableColumn<Evento, String> luogoTableColumn;
	
	@FXML
	private TableColumn<Evento, Button> reportTableColumn;
	
	@FXML
	private Button esciButton;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;
	
	private Elettore elettore;

	public ElencoReportController () {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}
	
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		dataOraInizioTableColumn.setStyle("-fx-alignment: CENTER;");
		dataOraInizioTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Evento, String> param) {
						return new SimpleStringProperty(param.getValue().getDataInizio().toString() + "\n ore: "
								+ param.getValue().getOraInizio());
					}
				});
		
		dataOraFineTableColumn.setStyle("-fx-alignment: CENTER;");
		dataOraFineTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Evento, String> param) {
						return new SimpleStringProperty(
								param.getValue().getDataFine().toString() + "\n ore: " + param.getValue().getOraFine());
					}
				});
		
		luogoTableColumn.setStyle("-fx-alignment: CENTER;");
		luogoTableColumn.setCellValueFactory(new PropertyValueFactory<>("luogo"));
		
		reportTableColumn.setStyle("-fx-alignment: CENTER;");
		reportTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button reportButton = new Button("Visualizza");
						reportButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								Prenotazione prenotazione = new Prenotazione();
								prenotazione.setElettore(elettore);
								prenotazione.setEvento(param.getValue());
								dispatcher.renderView("reportEvento", prenotazione); // FARE VISTA REPORT EVENTO
							}
						});
						return new SimpleObjectProperty<Button>(reportButton);
					}
				});
	
	}
	
	@Override
	public void initializeData(Elettore elettore) {
		try {
			this.elettore = elettore;
			List<Evento> eventi = eventoService.trovaEventiFinitiPrenotati(elettore);
			ObservableList<Evento> eventiData = FXCollections.observableArrayList(eventi);
			eventiTerminatiTable.setItems(eventiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
	
	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("homeElettore", elettore);
	}
	
	@FXML
	public void esciAction(MouseEvent event) {
		dispatcher.logout();
	};
	
}
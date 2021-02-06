package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.TurnazioneService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Turnazione;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ListaTurnazioniAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private Label turniLabel;

	@FXML
	private TableView<Turnazione> turniTable;

	@FXML
	private TableColumn<Turnazione, String> emailTableColumn;

	@FXML
	private TableColumn<Turnazione, String> fasciaTableColumn;

	@FXML
	private TableColumn<Turnazione, LocalDate> data_turnoTableColumn;

	@FXML
	private TableColumn<Turnazione, String> nome_eventoTableColumn;

	@FXML
	private TableColumn<Turnazione, String> luogoTableColumn;

	@FXML
	private TableColumn<Turnazione, Button> eliminaTableColumn;

	@FXML
	private Button aggiungiTurnoButton;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private TurnazioneService turnazioneService;

	private Amministratore amministratore;

	public ListaTurnazioniAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		turnazioneService = factory.getTurnazioneService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		emailTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Turnazione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Turnazione, String> param) {
						return new SimpleObjectProperty<String>(param.getValue().getOperatore().getEmail());
					}
				});
		fasciaTableColumn.setCellValueFactory(new PropertyValueFactory<>("fascia"));
		data_turnoTableColumn.setCellValueFactory(new PropertyValueFactory<>("data_turno"));
		nome_eventoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Turnazione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Turnazione, String> param) {
						return new SimpleObjectProperty<String>(param.getValue().getEvento().getNome());
					}
				});
		luogoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Turnazione, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Turnazione, String> param) {
						return new SimpleObjectProperty<String>(param.getValue().getEvento().getLuogo());
					}
				});
		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Turnazione, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Turnazione, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaTurnazione", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});

	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			this.amministratore = amministratore;
			List<Turnazione> turnazione = turnazioneService.visualizzaTutteLeTurnazioni();
			ObservableList<Turnazione> turnazioniData = FXCollections.observableArrayList(turnazione);
			turniTable.setItems(turnazioniData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiTurnoAction(ActionEvent event) {
		Turnazione turnazione = new Turnazione();
		dispatcher.renderView("aggiungiTurnazione", turnazione);
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("dashboardAmministratore", amministratore);
	}
}

package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Evento;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ListaEventiAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private Label eventiLabel;

	@FXML
	private TableView<Evento> eventiTable;

	@FXML
	private TableColumn<Evento, String> nomeTableColumn;

	@FXML
	private TableColumn<Evento, Button> regolamentoTableColumn;
	
	@FXML
	private TableColumn<Evento, String> oraInizioTableColumn;

	@FXML
	private TableColumn<Evento, String> oraFineTableColumn;

	@FXML
	private TableColumn<Evento, String> luogoTableColumn;

	@FXML
	private TableColumn<Evento, Integer> numero_preferenze_esprimibiliTableColumn;

	@FXML
	private TableColumn<Evento, Button> azioniTableColumn;

	@FXML
	private TableColumn<Evento, Button> azioni2TableColumn;

	@FXML
	private TableColumn<Evento, Button> eliminaTableColumn;

	@FXML
	private Button aggiungiEventoButton;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;
	private UtenteService utenteService;
	
	private Amministratore amministratore;
	private Evento evento;

	public ListaEventiAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		regolamentoTableColumn.setStyle("-fx-alignment: CENTER;");
		regolamentoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button regolamentoButton = new Button("Visualizza");
						regolamentoButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("regolamentoEventoAmministratore", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(regolamentoButton);
					}
				});		
		oraInizioTableColumn.setStyle("-fx-alignment: CENTER;");
		oraInizioTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Evento, String> param) {
						return new SimpleStringProperty(param.getValue().getDataInizio().toString() + "\n ore: "
								+ param.getValue().getOraInizio());
					}
				});
		
		oraFineTableColumn.setStyle("-fx-alignment: CENTER;");
		oraFineTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Evento, String> param) {
						return new SimpleStringProperty(
								param.getValue().getDataFine().toString() + "\n ore: " + param.getValue().getOraFine());
					}
				});
		luogoTableColumn.setStyle("-fx-alignment: CENTER;");
		luogoTableColumn.setCellValueFactory(new PropertyValueFactory<>("luogo"));
		
		numero_preferenze_esprimibiliTableColumn.setStyle("-fx-alignment: CENTER;");
		numero_preferenze_esprimibiliTableColumn
				.setCellValueFactory(new PropertyValueFactory<>("numero_preferenze_esprimibili"));

		azioniTableColumn.setStyle("-fx-alignment: CENTER;");
		azioniTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button visualizzaButton = new Button("Visualizza Candidati");
						visualizzaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("listaCandidatiAmministratore", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(visualizzaButton);
					}
				});

		azioni2TableColumn.setStyle("-fx-alignment: CENTER;");
		azioni2TableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button visualizzaButton = new Button("Visualizza Prenotati");
						visualizzaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("listaPrenotatiAmministratore", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(visualizzaButton);
					}
				});

		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaEvento", param.getValue());
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
			System.out.println(amministratore);
			List<Evento> evento = eventoService.trovaTuttiEventi();
			ObservableList<Evento> eventiData = FXCollections.observableArrayList(evento);
			eventiTable.setItems(eventiData);
			System.out.println(amministratore);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiEventoAction(ActionEvent event) {
		
		dispatcher.renderView("aggiungiEvento", evento);
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		System.out.println(amministratore);
		dispatcher.renderView("dashboardAmministratore", amministratore);
	}
}

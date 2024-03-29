package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ListaReportAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private Label reportLabel;

	@FXML
	private TableView<Evento> reportTable;

	@FXML
	private TableColumn<Evento, String> nomeTableColumn;

	@FXML
	private TableColumn<Evento, String> regolamentoTableColumn;

	@FXML
	private TableColumn<Evento, LocalDate> dataInizioTableColumn;

	@FXML
	private TableColumn<Evento, LocalDate> dataFineTableColumn;

	@FXML
	private TableColumn<Evento, String> luogoTableColumn;

	@FXML
	private TableColumn<Evento, String> report_risultatiTableColumn;

	@FXML
	private TableColumn<Evento, String> report_statisticheTableColumn;

	@FXML
	private TableColumn<Evento, Button> aggiungiReportTableColumn;

	@FXML
	private TableColumn<Evento, Button> aggiungiStatisticheTableColumn;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Amministratore amministratore;

	public ListaReportAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		regolamentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("regolamento"));
		dataInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
		dataFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
		luogoTableColumn.setCellValueFactory(new PropertyValueFactory<>("luogo"));
		report_risultatiTableColumn.setCellValueFactory(new PropertyValueFactory<>("report_risultati"));
		report_statisticheTableColumn.setCellValueFactory(new PropertyValueFactory<>("report_statistiche"));

		aggiungiStatisticheTableColumn.setStyle("-fx-alignment: CENTER;");
		aggiungiStatisticheTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button modificaButton = new Button("Scrivi Statistiche");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("aggiungiReport", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});

		aggiungiReportTableColumn.setStyle("-fx-alignment: CENTER;");
		aggiungiReportTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button modificaButton = new Button("Aggiungi Report");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								try {
									eventoService.creaReport(param.getValue());
								} catch (BusinessException e) {
									e.printStackTrace();
								}
								dispatcher.renderView("listaReportAmministratore", amministratore);
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});

	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			this.amministratore = amministratore;
			List<Evento> evento = eventoService.trovaEventiFiniti();
			ObservableList<Evento> eventiData = FXCollections.observableArrayList(evento);
			reportTable.setItems(eventiData);

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("dashboardAmministratore", amministratore);
	}
}

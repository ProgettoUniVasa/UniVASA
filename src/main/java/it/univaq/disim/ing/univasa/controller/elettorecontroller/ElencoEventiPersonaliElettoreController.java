package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.controller.pazientecontroller.Prescrizione;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ElencoEventiPersonaliElettoreController implements Initializable, DataInitializable<Elettore> {
	@FXML
	private TableView<Evento> eventiPersonaliTable;

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
	private TableColumn<Evento, String> statoTableColumn; // uso String per visualizzare la mia modalità di voto (in
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
	private Button esciButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService; // vedere service

	private Elettore elettore;

	public ElencoEventiPersonaliElettoreController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance(); // vedere business factory
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
								dispatcher.renderView("regolamentoEvento", param.getValue()); // creare vista
																								// regolamentoEvento
							}
						});
						return new SimpleObjectProperty<Button>(regolamentoButton);
					}
				});

		statoTableColumn.setStyle("-fx-alignment: CENTER;");
		statoTableColumn.setCellValueFactory(new PropertyValueFactory<>("stato")); // non esiste come attributo di
																					// Evento, si potrebbe mettere uno
																					// stato votazione/stato evento?

		statoTableColumn.setCellFactory(new Callback<TableColumn<Evento, String>, TableCell<Evento, String>>() {
			public TableCell<Evento, String> call(TableColumn<Evento, String> param) {
				return new TableCell<Evento, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							if (item.contains("Prenotazione Online")) {
								this.setTextFill(Color.GREEN);
							} else if (item.contains("Prenotazione In Sede")) {
								this.setTextFill(Color.ORANGE);
								//visualizzare vista per cambiare metodo di votazione
							} else if (item.contains("Evento terminato")) {
								this.setTextFill(Color.RED);
								//Visualizzare le statistiche se cliccato
							}
							setText(item);
							
						}
					}
				};
			}
		});

	}

	@Override
	public void initializeData(Elettore elettore) {
		try {
			this.elettore = elettore;
			List<Evento> eventi = eventoService.trovaEventiElettore(elettore); // vedere service
			ObservableList<Evento> eventiData = FXCollections.observableArrayList(eventi);
			eventiPersonaliTable.setItems(eventiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

}

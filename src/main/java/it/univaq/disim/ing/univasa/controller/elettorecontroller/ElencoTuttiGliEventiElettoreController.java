// vedere business factory
// vedere service
// creare vista regolamentoEvento
// creare vista prenotazioneEvento SCEGLIERE SE FARE DUE COLONNE E LE DUE VISTE SUCCESSIVE (UNA PER PRENOTAZIONE IN PRESENZA, L'ALTRA PER PRENOTAZIONE ONLINE)
// vedere parte codice bottone in alto a destra logout

package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.PrescrizioneService;
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
	private Button esciButton;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService; //vedere service

	private Elettore elettore;

	public ElencoTuttiGliEventiElettoreController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance(); //vedere business factory
		prescrizioneService = factory.getPrescrizioneService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome")); //vedere cosa fa "nome"
		dataOraInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("data e ora inizio")); //vedere cosa fa "data e ora inizio"
		dataOraFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("data e ora fine")); //vedere cosa fa "data e ora fine"
		luogoTableColumn.setCellValueFactory(new PropertyValueFactory<>("luogo")); //vedere cosa fa "luogo"

		regolamentoTableColumn.setStyle("-fx-alignment: CENTER;");
		regolamentoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Evento, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Evento, Button> param) {
						final Button regolamentoButton = new Button("Visualizza");
						regolamentoButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("regolamentoEvento", param.getValue()); //creare vista regolamentoEvento
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
								dispatcher.renderView("prenotazioneInPresenzaEvento", param.getValue()); //creare vista prenotazioneInPresenzaEvento
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
								dispatcher.renderView("prenotazioneOnlineEvento", param.getValue()); //creare vista prenotazioneInPresenzaEvento
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
			List<Evento> eventi = prescrizioneService.trovaPrescrizioniMedico(elettore); //vedere service
			ObservableList<Evento> eventiData = FXCollections.observableArrayList(eventi);
			tuttiGliEventiTable.setItems(eventiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	//parte codice bottone in alto a destra logout
	@FXML
	public void esciAction(ActionEvent event) throws BusinessException {
		Prescrizione prescrizione = new Prescrizione();
		prescrizione.setMedico(medico);
		prescrizione.setFirma(medico.getNome() + " " + medico.getCognome());
		prescrizione.setData(LocalDate.now());
		prescrizione.setPaziente(new Paziente());
		prescrizione.getPaziente().setNome("");
		prescrizione.getPaziente().setCognome("");

		dispatcher.renderView("aggiungiPrescrizione", prescrizione);
	}

}
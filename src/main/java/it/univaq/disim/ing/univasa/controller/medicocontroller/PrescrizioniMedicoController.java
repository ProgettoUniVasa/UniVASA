package it.univaq.disim.ing.univasa.controller.medicocontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.PrescrizioneService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Medico;
import it.univaq.disim.ing.univasa.domain.Paziente;
import it.univaq.disim.ing.univasa.domain.Prescrizione;
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

public class PrescrizioniMedicoController implements Initializable, DataInitializable<Medico> {

	@FXML
	private TableView<Prescrizione> prescrizioniTable;

	@FXML
	private TableColumn<Prescrizione, LocalDate> dataTableColumn;

	@FXML
	private TableColumn<Prescrizione, String> numeroTableColumn;

	@FXML
	private TableColumn<Prescrizione, Button> azioniTableColumn;

	@FXML
	private TableColumn<Prescrizione, Button> modificaTableColumn;

	@FXML
	private TableColumn<Prescrizione, Button> eliminaTableColumn;

	@FXML
	private Button aggiungiPrescrizioneButton;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private Medico medico;

	public PrescrizioniMedicoController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		dataTableColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
		numeroTableColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));

		azioniTableColumn.setStyle("-fx-alignment: CENTER;");
		azioniTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Prescrizione, Button> param) {
						final Button dettagliButton = new Button("Dettagli");
						dettagliButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("dettagliMedico", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(dettagliButton);
					}
				});

		modificaTableColumn.setStyle("-fx-alignment: CENTER;");
		modificaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Prescrizione, Button> param) {
						final Button modificaButton = new Button("Modifica");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("modificaPrescrizione", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});

		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Prescrizione, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaPrescrizione", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});

	}

	@Override
	public void initializeData(Medico medico) {
		try {
			this.medico = medico;
			List<Prescrizione> prescrizioni = prescrizioneService.trovaPrescrizioniMedico(medico);
			ObservableList<Prescrizione> prescrizioniData = FXCollections.observableArrayList(prescrizioni);
			prescrizioniTable.setItems(prescrizioniData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiPrescrizioneAction(ActionEvent event) throws BusinessException {
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

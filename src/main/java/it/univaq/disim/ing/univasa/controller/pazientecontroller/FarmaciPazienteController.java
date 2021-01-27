package it.univaq.disim.ing.univasa.controller.pazientecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.PrescrizioneService;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.FarmacoPrescritto;
import it.univaq.disim.ing.univasa.domain.Prescrizione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class FarmaciPazienteController implements Initializable, DataInitializable<Prescrizione> {

	@FXML
	private Button indietroButton;

	@FXML
	private TableView<FarmacoPrescritto> farmaciPrescrittiTable;

	@FXML
	private TableColumn<FarmacoPrescritto, String> nomeTableColumn;

	@FXML
	private TableColumn<FarmacoPrescritto, String> principioAttivoTableColumn;

	@FXML
	private TableColumn<FarmacoPrescritto, String> produttoreTableColumn;

	@FXML
	private TableColumn<FarmacoPrescritto, LocalDate> scadenzaTableColumn;

	@FXML
	private TableColumn<FarmacoPrescritto, Double> costoTableColumn;

	@FXML
	private TableColumn<FarmacoPrescritto, Integer> quantitaPrescrittaTableColumn;

	private Prescrizione prescrizione;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private UtenteService utenteService;

	public FarmaciPazienteController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		nomeTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<FarmacoPrescritto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<FarmacoPrescritto, String> param) {
						return new SimpleStringProperty(param.getValue().getFarmaco().getNome());
					}
				});

		principioAttivoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<FarmacoPrescritto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<FarmacoPrescritto, String> param) {
						return new SimpleStringProperty(param.getValue().getFarmaco().getPrincipioAttivo());
					}
				});

		produttoreTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<FarmacoPrescritto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<FarmacoPrescritto, String> param) {
						return new SimpleStringProperty(param.getValue().getFarmaco().getProduttore());
					}
				});

		scadenzaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<FarmacoPrescritto, LocalDate>, ObservableValue<LocalDate>>() {
					@Override
					public ObservableValue<LocalDate> call(CellDataFeatures<FarmacoPrescritto, LocalDate> param) {
						return new SimpleObjectProperty<LocalDate>(param.getValue().getFarmaco().getScadenza());
					}
				});

		costoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<FarmacoPrescritto, Double>, ObservableValue<Double>>() {
					@Override
					public ObservableValue<Double> call(CellDataFeatures<FarmacoPrescritto, Double> param) {
						return new SimpleObjectProperty<Double>(param.getValue().getFarmaco().getCosto());
					}
				});

		quantitaPrescrittaTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantita"));
	}

	@Override
	public void initializeData(Prescrizione prescrizione) {
		try {
			this.prescrizione = prescrizione;
			List<FarmacoPrescritto> farmaciPrescritti = prescrizioneService.trovaFarmaciPrescritti(prescrizione);
			ObservableList<FarmacoPrescritto> farmaciPrescrittiData = FXCollections
					.observableArrayList(farmaciPrescritti);
			farmaciPrescrittiTable.setItems(farmaciPrescrittiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void indietroAction(ActionEvent event) throws BusinessException {
		dispatcher.renderView("prescrizioniPaziente", prescrizione.getPaziente());
	}

}

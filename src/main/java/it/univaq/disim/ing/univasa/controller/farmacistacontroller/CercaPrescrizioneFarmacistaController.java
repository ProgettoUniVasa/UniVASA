package it.univaq.disim.ing.univasa.controller.farmacistacontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.PrescrizioneService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Farmacista;
import it.univaq.disim.ing.univasa.domain.FarmacoPrescritto;
import it.univaq.disim.ing.univasa.domain.Prescrizione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class CercaPrescrizioneFarmacistaController implements Initializable, DataInitializable<Prescrizione> {

	@FXML
	private TableView<FarmacoPrescritto> farmaciPrescrittiTable;

	@FXML
	private TableColumn<FarmacoPrescritto, String> nomeTableColumn;

	@FXML
	private TableColumn<FarmacoPrescritto, Integer> quantitaTableColumn;

	@FXML
	private TextField numero;

	@FXML
	private DatePicker data;

	@FXML
	private TextField firma;

	@FXML
	private TextField costo;

	@FXML
	private Label nomePaziente;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private Prescrizione prescrizione;

	private Farmacista farmacista;

	public CercaPrescrizioneFarmacistaController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nomeTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<FarmacoPrescritto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<FarmacoPrescritto, String> param) {
						return new SimpleStringProperty(param.getValue().getFarmaco().getNome());
					}
				});

		quantitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantita"));

	}

	@Override
	public void initializeData(Prescrizione prescrizione) {
		List<FarmacoPrescritto> farmaciPrescritti;

		try {
			this.prescrizione = prescrizione;
			this.numero.setText(prescrizione.getNumero());
			this.data.setValue(prescrizione.getData());
			this.firma.setText(prescrizione.getFirma());

			// Calcolo del costo della prescrizione
			double costoTot = (Math.round(prescrizione.getCostoPrescrizione() * 100) / 100);

			for (FarmacoPrescritto f : prescrizioneService.trovaFarmaciPrescritti(prescrizione)) {
				Double costoFarmaco = f.getFarmaco().getCosto() * (f.getQuantita());
				costoFarmaco = (double) (Math.round(costoFarmaco * 100) / 100);
				costoTot += costoFarmaco;
			}

			this.costo.setText("" + costoTot);

			StringBuilder testo = new StringBuilder();
			testo.append(prescrizione.getPaziente().getNome());
			testo.append(" ");
			testo.append(prescrizione.getPaziente().getCognome());
			nomePaziente.setText(testo.toString());

			farmaciPrescritti = prescrizioneService.trovaFarmaciPrescritti(prescrizione);
			ObservableList<FarmacoPrescritto> farmaciData = FXCollections.observableArrayList(farmaciPrescritti);
			farmaciPrescrittiTable.setItems(farmaciData);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("prescrizioniFarmacista", farmacista);
	}

}

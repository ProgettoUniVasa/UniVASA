package it.univaq.disim.oop.pharma.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.PrescrizioneService;
import it.univaq.disim.oop.pharma.domain.FarmacoPrescritto;
import it.univaq.disim.oop.pharma.domain.Prescrizione;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class PrescrizioneCercataController implements Initializable, DataInitializable<Prescrizione> {

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
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private Prescrizione prescrizione;

	public PrescrizioneCercataController() {
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
			this.costo.setText("" + prescrizione.getCostoPrescrizione());
			farmaciPrescritti = prescrizioneService.trovaFarmaciPrescritti(prescrizione);
			ObservableList<FarmacoPrescritto> farmaciData = FXCollections.observableArrayList(farmaciPrescritti);
			farmaciPrescrittiTable.setItems(farmaciData);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("prescrizioniPaziente", prescrizione.getPaziente());
	}

}

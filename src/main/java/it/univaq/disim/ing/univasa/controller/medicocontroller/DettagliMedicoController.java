package it.univaq.disim.ing.univasa.controller.medicocontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.PrescrizioneService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DettagliMedicoController implements Initializable, DataInitializable<Prescrizione> {

	@FXML
	private TableView<FarmacoPrescritto> dettagliTable;

	@FXML
	private TableColumn<FarmacoPrescritto, String> nomeFarmacoTableColumn;

	@FXML
	private TableColumn<FarmacoPrescritto, Integer> quantitaPrescrittaTableColumn;

	@FXML
	private Button indietroButton;

	@FXML
	private Label pazienteLabel;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private Prescrizione prescrizione;

	public DettagliMedicoController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nomeFarmacoTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<FarmacoPrescritto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<FarmacoPrescritto, String> param) {
						return new SimpleStringProperty(param.getValue().getFarmaco().getNome());
					}
				});

		quantitaPrescrittaTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantita"));
	}

	@Override
	public void initializeData(Prescrizione prescrizione) {
		try {
			this.prescrizione = prescrizione;
			List<FarmacoPrescritto> farmaciPrescritti = prescrizioneService.trovaFarmaciPrescritti(prescrizione);
			ObservableList<FarmacoPrescritto> farmaciData = FXCollections.observableArrayList(farmaciPrescritti);

			StringBuilder testo = new StringBuilder();
			testo.append(prescrizione.getPaziente().getNome());
			testo.append(" ");
			testo.append(prescrizione.getPaziente().getCognome());
			pazienteLabel.setText(testo.toString());

			dettagliTable.setItems(farmaciData);

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void indietroAction(ActionEvent event) throws BusinessException {
		dispatcher.renderView("prescrizioniMedico", prescrizione.getMedico());
	}
}
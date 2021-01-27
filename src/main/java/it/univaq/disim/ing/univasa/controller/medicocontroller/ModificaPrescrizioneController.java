package it.univaq.disim.ing.univasa.controller.medicocontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.PrescrizioneService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Prescrizione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ModificaPrescrizioneController implements Initializable, DataInitializable<Prescrizione> {

	@FXML
	private TextField numero;

	@FXML
	private DatePicker dataPrescrizione;

	@FXML
	private TextField costoPrescrizione;

	@FXML
	private Button salvaPrescrizioneButton;

	@FXML
	private Button annullaPrescrizioneButton;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private Prescrizione prescrizione;

	public ModificaPrescrizioneController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Prescrizione prescrizione) {
		this.prescrizione = prescrizione;
		this.prescrizione.setMedico(prescrizione.getMedico());
		this.numero.setText(prescrizione.getNumero());
		this.dataPrescrizione.setValue(prescrizione.getData());
		this.dataPrescrizione.setEditable(false);
		this.costoPrescrizione.setText("" + prescrizione.getCostoPrescrizione());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		salvaPrescrizioneButton.disableProperty()
				.bind((numero.textProperty().isEmpty().or(dataPrescrizione.valueProperty().isNull())));
	}

	@FXML
	public void salvaPrescrizioneAction(ActionEvent event) {
		try {

			prescrizione.setNumero(numero.getText());
			prescrizione.setData(dataPrescrizione.getValue());
			prescrizione.setCostoPrescrizione(Double.parseDouble(costoPrescrizione.getText()));
			prescrizioneService.aggiornaPrescrizione(prescrizione);

			dispatcher.renderView("prescrizioniMedico", prescrizione.getMedico());

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaPrescrizioneAction(ActionEvent event) {
		dispatcher.renderView("prescrizioniMedico", prescrizione.getMedico());
	}
}

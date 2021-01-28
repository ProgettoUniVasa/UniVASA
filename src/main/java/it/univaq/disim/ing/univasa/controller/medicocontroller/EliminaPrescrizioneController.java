package it.univaq.disim.ing.univasa.controller.medicocontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Medico;
import it.univaq.disim.ing.univasa.domain.Prescrizione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EliminaPrescrizioneController implements Initializable, DataInitializable<Prescrizione> {

	@FXML
	private TextField numero;

	@FXML
	private DatePicker data;

	@FXML
	private TextField firma;

	@FXML
	private TextField costo;

	@FXML
	private Button eliminaButton;

	@FXML
	private Button annullaButton;

	private Prescrizione prescrizione;

	private Medico medico;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	public EliminaPrescrizioneController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	@Override
	public void initializeData(Prescrizione prescrizione) {
		this.prescrizione = prescrizione;
		this.medico = prescrizione.getMedico();
		this.numero.setText(prescrizione.getNumero());
		this.data.setValue(prescrizione.getData());
		this.firma.setText(prescrizione.getFirma());
		this.costo.setText("" + prescrizione.getCostoPrescrizione());
	}

	@FXML
	public void eliminaAction(ActionEvent event) {
		try {

			prescrizioneService.eliminaPrescrizione(prescrizione);
			prescrizioneService.eliminaFarmaciPrescritti(prescrizione);
			dispatcher.renderView("prescrizioniMedico", medico);

		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("prescrizioniMedico", prescrizione.getMedico());
	}
}
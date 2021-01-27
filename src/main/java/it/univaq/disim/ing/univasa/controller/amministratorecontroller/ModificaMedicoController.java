package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Medico;
import it.univaq.disim.ing.univasa.domain.TipologiaMedico;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ModificaMedicoController implements Initializable, DataInitializable<Medico> {

	@FXML
	private TextField residenza;

	@FXML
	private TextField telefono;

	@FXML
	private ComboBox<TipologiaMedico> tipologia;

	@FXML
	private Button salvaMedicoButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Medico medico = new Medico();

	private Amministratore amministratore;

	public ModificaMedicoController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tipologia.getItems().addAll(TipologiaMedico.values());
	}

	@Override
	public void initializeData(Medico medico) {
		this.medico = medico;
		this.residenza.setText(medico.getResidenza());
		this.telefono.setText(medico.getTelefono());
		this.tipologia.setValue(medico.getTipologia());
	}

	@FXML
	public void salvaMedicoAction(ActionEvent event) {
		try {
			// Controllo sul numero di telefono che deve essere lungo 10 cifre e non può
			// contenere lettere
			if (telefono.getLength() != 10 || !telefono.getText().matches("^[0-9]+$")) {
				JOptionPane.showMessageDialog(null, " Il numero di telefono deve essere di 10 cifre!", "ATTENZIONE",
						JOptionPane.WARNING_MESSAGE);
			} else {
				medico.setResidenza(residenza.getText());
				medico.setTelefono(telefono.getText());
				medico.setTipologia(tipologia.getValue());

				utenteService.aggiornaMedico(medico);

				dispatcher.renderView("gestioneMediciAmministratore", amministratore);
			}
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("gestioneMediciAmministratore", amministratore);
	}
}
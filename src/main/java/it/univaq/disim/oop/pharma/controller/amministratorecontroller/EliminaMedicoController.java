package it.univaq.disim.oop.pharma.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.UtenteService;
import it.univaq.disim.oop.pharma.controller.DataInitializable;
import it.univaq.disim.oop.pharma.domain.Amministratore;
import it.univaq.disim.oop.pharma.domain.Medico;
import it.univaq.disim.oop.pharma.domain.TipologiaMedico;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EliminaMedicoController implements Initializable, DataInitializable<Medico> {

	@FXML
	private TextField nome;

	@FXML
	private TextField cognome;

	@FXML
	private TextField cf;

	@FXML
	private TextField residenza;

	@FXML
	private TextField telefono;

	@FXML
	private TextField codiceAlbo;

	@FXML
	private ComboBox<TipologiaMedico> tipologia;

	@FXML
	private Button eliminaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Medico medico;

	private Amministratore amministratore;

	public EliminaMedicoController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Medico medico) {
		this.medico = medico;
		this.nome.setText(medico.getNome());
		this.cognome.setText(medico.getCognome());
		this.cf.setText(medico.getCf());
		this.residenza.setText(medico.getResidenza());
		this.telefono.setText(medico.getTelefono());
		this.codiceAlbo.setText(medico.getCodiceAlboMedici());
		this.tipologia.setValue(medico.getTipologia());

	}

	@FXML
	public void eliminaAction(ActionEvent event) {

		try {

			utenteService.eliminaUtente(medico);
			dispatcher.renderView("gestioneMediciAmministratore", amministratore);

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("gestioneMediciAmministratore", amministratore);
	}
}
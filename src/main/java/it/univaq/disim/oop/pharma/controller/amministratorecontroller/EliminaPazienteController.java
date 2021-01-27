package it.univaq.disim.oop.pharma.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.UtenteService;
import it.univaq.disim.oop.pharma.controller.DataInitializable;
import it.univaq.disim.oop.pharma.domain.Amministratore;
import it.univaq.disim.oop.pharma.domain.Paziente;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EliminaPazienteController implements Initializable, DataInitializable<Paziente> {

	@FXML
	private TextField nome;

	@FXML
	private TextField cognome;

	@FXML
	private TextField cf;

	@FXML
	private TextField luogoNascita;

	@FXML
	private DatePicker dataNascita;

	@FXML
	private TextField residenza;

	@FXML
	private TextField telefono;

	@FXML
	private Button eliminaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Paziente paziente;

	private Amministratore amministratore;

	public EliminaPazienteController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Paziente paziente) {
		this.paziente = paziente;
		this.nome.setText(paziente.getNome());
		this.cognome.setText(paziente.getCognome());
		this.cf.setText(paziente.getCf());
		this.luogoNascita.setText(paziente.getLuogoNascita());
		this.dataNascita.setValue(paziente.getDataNascita());
		this.residenza.setText(paziente.getResidenza());
		this.telefono.setText(paziente.getTelefono());
	}

	@FXML
	public void eliminaAction(ActionEvent event) {

		try {

			utenteService.eliminaUtente(paziente);
			dispatcher.renderView("gestionePazientiAmministratore", amministratore);

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("gestionePazientiAmministratore", amministratore);
	}
}
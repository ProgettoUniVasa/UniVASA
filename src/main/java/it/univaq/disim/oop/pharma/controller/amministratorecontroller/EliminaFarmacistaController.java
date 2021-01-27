package it.univaq.disim.oop.pharma.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.UtenteService;
import it.univaq.disim.oop.pharma.controller.DataInitializable;
import it.univaq.disim.oop.pharma.domain.Amministratore;
import it.univaq.disim.oop.pharma.domain.Farmacista;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EliminaFarmacistaController implements Initializable, DataInitializable<Farmacista> {

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
	private Button eliminaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Farmacista farmacista;

	private Amministratore amministratore;

	public EliminaFarmacistaController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Farmacista farmacista) {
		this.farmacista = farmacista;
		this.nome.setText(farmacista.getNome());
		this.cognome.setText(farmacista.getCognome());
		this.cf.setText(farmacista.getCf());
		this.residenza.setText(farmacista.getResidenza());
		this.telefono.setText(farmacista.getTelefono());
	}

	@FXML
	public void eliminaAction(ActionEvent event) {

		try {

			utenteService.eliminaUtente(farmacista);
			dispatcher.renderView("gestioneFarmacistiAmministratore", amministratore);

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("gestioneFarmacistiAmministratore", amministratore);
	}
}
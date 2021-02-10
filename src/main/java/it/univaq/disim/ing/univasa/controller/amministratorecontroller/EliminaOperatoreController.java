package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.TurnazioneService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EliminaOperatoreController implements Initializable, DataInitializable<Operatore> {

	@FXML
	private TextField nome;

	@FXML
	private TextField cognome;

	@FXML
	private TextField email;

	@FXML
	private TextField telefono;

	@FXML
	private TextField nome_università;

	@FXML
	private TextField dipartimento;

	@FXML
	private Button eliminaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;
	private TurnazioneService turnazioniService;

	private Operatore operatore;

	private Amministratore amministratore;

	public EliminaOperatoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
		turnazioniService = factory.getTurnazioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Operatore operatore) {
		this.operatore = operatore;
		this.nome.setText(operatore.getNome());
		this.cognome.setText(operatore.getCognome());
		this.email.setText(operatore.getEmail());
		this.telefono.setText(operatore.getTelefono());
		this.nome_università.setText(operatore.getNome_università());
		this.dipartimento.setText(operatore.getDipartimento());
	}

	@FXML
	public void eliminaAction(ActionEvent event) {
		try {
			if (turnazioniService.visualizzaTurnazioni(operatore).size() == 0) {
				utenteService.eliminaUtente(operatore);
				JOptionPane.showMessageDialog(null, "Operatore eliminato con successo!", " ",
						JOptionPane.INFORMATION_MESSAGE);
				dispatcher.renderView("gestioneOperatoriAmministratore", amministratore);
			} else {
				JOptionPane.showMessageDialog(null, "L'operatore selezionato ha dei turni di lavoro assegnati! Non può essere eliminato.",
						"Operatore con turni assegnati", JOptionPane.ERROR_MESSAGE);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("gestioneOperatoriAmministratore", amministratore);
	}
}
package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModificaAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private TextField email;

	@FXML
	private TextField telefono;

	@FXML
	private TextField nome_università;

	@FXML
	private TextField dipartimento;

	@FXML
	private Button salvaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Amministratore amministratore;

	public ModificaAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Amministratore amministratore) {
		this.amministratore = amministratore;
		this.email.setText(amministratore.getEmail());
		this.telefono.setText(amministratore.getTelefono());
		this.nome_università.setText(amministratore.getNome_università());
		this.dipartimento.setText(amministratore.getDipartimento());
	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {
			// Controllo sul numero di telefono che deve essere lungo 10 cifre e non può
			// contenere lettere
			if (telefono.getLength() != 10 || !telefono.getText().matches("^[0-9]+$")) {
				JOptionPane.showMessageDialog(null, " Il numero di telefono deve essere di 10 cifre!", "ATTENZIONE",
						JOptionPane.WARNING_MESSAGE);
			} else
			// controllo @ per email
			if (!email.getText().contains("@")) {
				JOptionPane.showMessageDialog(null, "Email non valida!", "ATTENZIONE", JOptionPane.WARNING_MESSAGE);
			} else {
				amministratore.setEmail(email.getText());
				amministratore.setTelefono(telefono.getText());
				amministratore.setNome_università(nome_università.getText());
				amministratore.setDipartimento(dipartimento.getText());

				utenteService.modificaAmministratore(amministratore);
				dispatcher.renderView("gestioneAmministratoriAmministratore", amministratore);
			}
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("gestioneAmministratoriAmministratore", amministratore);
	}
}

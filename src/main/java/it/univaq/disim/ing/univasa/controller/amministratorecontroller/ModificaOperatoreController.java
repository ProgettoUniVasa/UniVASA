package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import it.univaq.disim.ing.univasa.business.BusinessException;
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

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ModificaOperatoreController implements Initializable, DataInitializable<Operatore> {

	@FXML
	private TextField email;

	@FXML
	private TextField telefono;

	@FXML
	private TextField universita;

	@FXML
	private TextField dipartimento;

	@FXML
	private Button salvaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Operatore operatore;

	private Amministratore amministratore;

	public ModificaOperatoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Operatore operatore) {
		this.operatore = operatore;
		this.email.setText(operatore.getEmail());
		this.telefono.setText(operatore.getTelefono());
		this.universita.setText(operatore.getNome_università());
		this.dipartimento.setText(operatore.getDipartimento());
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
					JOptionPane.showMessageDialog(null, "Email non valida", "ATTENZIONE",
							JOptionPane.WARNING_MESSAGE);
				}else{
				operatore.setEmail(email.getText());
				operatore.setTelefono(telefono.getText());
				operatore.setNome_università(universita.getText());
				operatore.setDipartimento(dipartimento.getText());

				utenteService.modificaOperatore(operatore);

				dispatcher.renderView("gestioneOperatoriAmministratore", amministratore);
			}
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("gestioneOperatoriAmministratore", amministratore);
	}
}

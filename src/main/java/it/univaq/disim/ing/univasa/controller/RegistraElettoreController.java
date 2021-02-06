package it.univaq.disim.ing.univasa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Professione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RegistraElettoreController implements Initializable, DataInitializable<Elettore> {

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	@FXML
	private TextField nome;

	@FXML
	private TextField cognome;

	@FXML
	private TextField email;

	@FXML
	private DatePicker data_nascita;

	@FXML
	private TextField telefono;

	@FXML
	private ComboBox<Professione> professione;

	@FXML
	private TextField nome_università;

	@FXML
	private TextField dipartimento;

	@FXML
	private TextField matricola;

	@FXML
	private Button registraButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Elettore elettore = new Elettore();

	public RegistraElettoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		professione.getItems().addAll(Professione.values());
	}

	@Override
	public void initializeData(Elettore elettore) {

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		registraButton.disableProperty()
				.bind((username.textProperty().isEmpty()).or(password.textProperty().isEmpty())
						.or(nome.textProperty().isEmpty()).or(cognome.textProperty().isEmpty())
						.or(email.textProperty().isEmpty()).or(data_nascita.valueProperty().isNull())
						.or(telefono.textProperty().isEmpty()).or(professione.valueProperty().isNull())
						.or(nome_università.textProperty().isEmpty()).or(dipartimento.textProperty().isEmpty())
						.or(matricola.textProperty().isEmpty()));

	}

	@FXML
	public void registraElettoreAction(ActionEvent event) {
		try {
			int c = 0;
			// Controllo sull'elettore
			for (Elettore e : utenteService.trovaTuttiElettori()) {
				if (e.getUsername().equals(username.getText()) || e.getEmail().equals(email.getText())) {
					c++;
					JOptionPane.showMessageDialog(null, "Questo nome utente è gia esistente!", "Errore",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			// Controllo sul numero di telefono che deve essere lungo 10 cifre e non può
			// contenere lettere
			if (telefono.getLength() != 10 || !telefono.getText().matches("^[0-9]+$")) {
				JOptionPane.showMessageDialog(null, " Il numero di telefono deve essere di 10 cifre!", "ATTENZIONE",
						JOptionPane.WARNING_MESSAGE);
			} else {
				if (c == 0 && elettore.getId() == null) {
					elettore.setUsername(username.getText());
					elettore.setPassword(password.getText());
					elettore.setNome(nome.getText());
					elettore.setCognome(cognome.getText());
					elettore.setEmail(email.getText());
					elettore.setData_nascita(data_nascita.getValue());
					elettore.setTelefono(telefono.getText());
					elettore.setProfessione(professione.getValue());
					elettore.setNome_università(nome_università.getText());
					elettore.setDipartimento(dipartimento.getText());
					elettore.setMatricola(matricola.getText());
					utenteService.creaElettore(elettore);
					dispatcher.logout();
				} else {
					dispatcher.logout();
				}
			}

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.logout();
	}
}

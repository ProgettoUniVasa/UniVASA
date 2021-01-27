package it.univaq.disim.oop.pharma.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.UtenteService;
import it.univaq.disim.oop.pharma.domain.Paziente;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RegistraPazienteController implements Initializable, DataInitializable<Paziente> {

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	@FXML
	private TextField nome;

	@FXML
	private TextField cognome;

	@FXML
	private ComboBox<String> sesso;

	@FXML
	private TextField cf;

	@FXML
	private DatePicker dataNascita;

	@FXML
	private TextField luogoNascita;

	@FXML
	private TextField residenza;

	@FXML
	private TextField telefono;

	@FXML
	private TextField saldo;

	@FXML
	private Button registraButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Paziente paziente = new Paziente();

	public RegistraPazienteController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sesso.getItems().addAll("Maschio", "Femmina");
	}

	@Override
	public void initializeData(Paziente paziente) {

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		registraButton.disableProperty()
				.bind((username.textProperty().isEmpty()).or(password.textProperty().isEmpty())
						.or(nome.textProperty().isEmpty()).or(cognome.textProperty().isEmpty())
						.or(sesso.accessibleTextProperty().isEqualTo(sesso)).or(cf.textProperty().isEmpty())
						.or(dataNascita.valueProperty().isNull()).or(luogoNascita.textProperty().isEmpty())
						.or(residenza.textProperty().isEmpty()));

	}

	@FXML
	public void registraPazienteAction(ActionEvent event) {
		try {
			int c = 0;
			for (Paziente p : utenteService.trovaTuttiPazienti()) {
				if (p.getUsername().equals(username.getText()) || p.getCf().equals(cf.getText())) {
					c++;
					JOptionPane.showMessageDialog(null, " Questo nome utente è gia esistente", "Errore",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			// Controllo sul numero di telefono che deve essere lungo 10 cifre e non può
			// contenere lettere
			if (telefono.getLength() != 10 || !telefono.getText().matches("^[0-9]+$")) {
				JOptionPane.showMessageDialog(null, " Il numero di telefono deve essere di 10 cifre!", "ATTENZIONE",
						JOptionPane.WARNING_MESSAGE);
				// Controllo sul saldo che non può contenere caratteri alfabetici
			} else if (!saldo.getText().matches("^[0-9.,]+$")) {
				JOptionPane.showMessageDialog(null, " Il saldo può contenere solo caratteri numerici!", "ATTENZIONE",
						JOptionPane.WARNING_MESSAGE);
			} else {
				if (c == 0 && paziente.getId() == null) {
					paziente.setUsername(username.getText());
					paziente.setPassword(password.getText());
					paziente.setNome(nome.getText());
					paziente.setCognome(cognome.getText());
					paziente.setSesso(sesso.getValue());
					paziente.setCf(cf.getText());
					paziente.setDataNascita(dataNascita.getValue());
					paziente.setLuogoNascita(luogoNascita.getText());
					paziente.setResidenza(residenza.getText());
					paziente.setTelefono(telefono.getText());
					paziente.setSaldo(Double.parseDouble(saldo.getText()));
					utenteService.creaPaziente(paziente);
					dispatcher.logout();
				} else {
					utenteService.aggiornaPaziente(paziente);
					dispatcher.logout();
				}
			}

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.registratiAction(paziente);
	}
}

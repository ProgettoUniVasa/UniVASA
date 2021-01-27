package it.univaq.disim.oop.pharma.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.UtenteService;
import it.univaq.disim.oop.pharma.domain.Medico;
import it.univaq.disim.oop.pharma.domain.TipologiaMedico;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RegistraMedicoController implements Initializable, DataInitializable<Medico> {

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
	private TextField codiceAlbo;

	@FXML
	private ComboBox<TipologiaMedico> tipologia;

	@FXML
	private Button registraButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Medico medico = new Medico();

	public RegistraMedicoController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sesso.getItems().addAll("Maschio", "Femmina");
		tipologia.getItems().addAll(TipologiaMedico.values());
	}

	@Override
	public void initializeData(Medico medico) {

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		registraButton.disableProperty()
				.bind((username.textProperty().isEmpty()).or(password.textProperty().isEmpty())
						.or(nome.textProperty().isEmpty()).or(cognome.textProperty().isEmpty())
						.or(sesso.accessibleTextProperty().isEqualTo(sesso)).or(cf.textProperty().isEmpty())
						.or(dataNascita.valueProperty().isNull()).or(luogoNascita.textProperty().isEmpty())
						.or(residenza.textProperty().isEmpty()).or(codiceAlbo.textProperty().isEmpty())
						.or(tipologia.accessibleTextProperty().isEqualTo(tipologia)));
	}

	@FXML
	public void registraMedicoAction(ActionEvent event) {
		try {
			int c = 0;
			for (Medico m : utenteService.trovaTuttiMedici()) {
				if (m.getUsername().equals(username.getText()) || m.getCf().equals(cf.getText())) {
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
			} else {
				if (c == 0 && medico.getId() == null) {
					medico.setUsername(username.getText());
					medico.setPassword(password.getText());
					medico.setNome(nome.getText());
					medico.setCognome(cognome.getText());
					medico.setSesso(sesso.getValue());
					medico.setCf(cf.getText());
					medico.setDataNascita(dataNascita.getValue());
					medico.setLuogoNascita(luogoNascita.getText());
					medico.setResidenza(residenza.getText());
					medico.setTelefono(telefono.getText());
					medico.setCodiceAlboMedici(codiceAlbo.getText());
					medico.setTipologia(tipologia.getValue());
					utenteService.creaMedico(medico);
					dispatcher.logout();
				} else {
					utenteService.aggiornaMedico(medico);
					dispatcher.logout();
				}
			}
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.registratiAction(medico);
	}
}

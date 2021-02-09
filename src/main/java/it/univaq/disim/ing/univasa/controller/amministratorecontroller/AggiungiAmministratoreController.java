package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Professione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AggiungiAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private TextField nome;

	@FXML
	private TextField cognome;

	@FXML
	private TextField email;

	@FXML
	private TextField telefono;

	@FXML
	private DatePicker dataNascita;

	@FXML
	private ComboBox<Professione> professione;

	@FXML
	private TextField nome_università;

	@FXML
	private TextField dipartimento;

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	@FXML
	private Button aggiungiAmministratoreButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Amministratore amministratore = new Amministratore();

	public AggiungiAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		professione.getItems().addAll(Professione.values());
	}

	@Override
	public void initializeData(Amministratore amministratore) {
		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		aggiungiAmministratoreButton.disableProperty().bind((nome.textProperty().isEmpty()
				.or(cognome.textProperty().isEmpty().or(email.textProperty().isEmpty()
						.or(telefono.textProperty().isEmpty().or(dataNascita.valueProperty().isNull()
								.or(professione.accessibleTextProperty().isEqualTo(professione)
										.or(nome_università.textProperty().isEmpty()
												.or(dipartimento.textProperty().isEmpty().or(username.textProperty()
														.isEmpty().or(password.textProperty().isEmpty())))))))))));
	}

	@FXML
	public void aggiungiAmministratoreAction(ActionEvent event) {
		try {

			// Variabile che conta il numero di operatori con email o telefono che si vuole
			// inserire
			int c = 0;
			amministratore.setNome(nome.getText());
			amministratore.setCognome(cognome.getText());
			amministratore.setEmail(email.getText());
			amministratore.setTelefono(telefono.getText());
			amministratore.setData_nascita(dataNascita.getValue());
			amministratore.setProfessione(professione.getValue());
			amministratore.setNome_università(nome_università.getText());
			amministratore.setDipartimento(dipartimento.getText());
			amministratore.setUsername(username.getText());
			amministratore.setPassword(password.getText());

			for (Amministratore a : utenteService.trovaTuttiAmministratori()) {
				if (a.getEmail().equals(email.getText()) || a.getTelefono().equals(telefono.getText())) {
					c++;
					JOptionPane.showMessageDialog(null, "Esiste già questo amministratore", "Errore",
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
				if (c == 0 && amministratore.getId() == null) {
					utenteService.creaAmministratore(amministratore);
					JOptionPane.showMessageDialog(null, "Amministratore aggiunto con successo!", " ",
							JOptionPane.INFORMATION_MESSAGE);
					dispatcher.renderView("gestioneAmministratoriAmministratore", amministratore);
				}
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

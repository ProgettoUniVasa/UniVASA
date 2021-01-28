package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AggiungiOperatoreController implements Initializable, DataInitializable<Operatore> {

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
	private TextField professione;

	@FXML
	private TextField nome_università;

	@FXML
	private TextField dipartimento;

	@FXML
	private Button aggiungiOperatoreButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Operatore operatore = new Operatore();

	private Amministratore amministratore;

	public AggiungiOperatoreController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Operatore operatore) {
		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		aggiungiOperatoreButton.disableProperty()
				.bind((nome.textProperty().isEmpty()
						.or(cognome.textProperty().isEmpty()
								.or(email.textProperty().isEmpty().or(telefono.textProperty().isEmpty()
										.or(dataNascita.valueProperty().isNull().or(
												professione.textProperty().isEmpty().or(nome_università.textProperty()
														.isEmpty().or(dipartimento.textProperty().isEmpty())))))))));
	}

	@FXML
	public void aggiungiOperatoreAction(ActionEvent event) {
		try {

			// Variabile che conta il numero di farmacisti con il cf che si vuogliono
			// inserire
			int c = 0;
			operatore.setNome(nome.getText());
			operatore.setCognome(cognome.getText());
			operatore.setEmail(email.getText());
			operatore.setTelefono(telefono.getText());
			operatore.setDataNascita(dataNascita.getValue());
			operatore.setProfessione(professione.getValue());
			operatore.setNome_università(nome_università.getText());
			operatore.setDipartimento(dipartimento.getText());

			for (Operatore f : utenteService.trovaTuttiOperatori()) {
				if (f.getEmail().equals(email.getText()) || f.getTelefono().equals(telefono.getText())) {
					c++;
					JOptionPane.showMessageDialog(null, "Esiste già questo operatore", "Errore",
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
				if (c == 0 && operatore.getId() == null) {
					utenteService.creaOperatore(operatore);
					dispatcher.renderView("gestioneOperatoriAmministratore", amministratore);
				}
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

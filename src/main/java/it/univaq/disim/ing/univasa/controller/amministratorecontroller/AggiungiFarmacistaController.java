package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Farmacista;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AggiungiFarmacistaController implements Initializable, DataInitializable<Farmacista> {

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
	private Button aggiungiFarmacistaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Farmacista farmacista = new Farmacista();

	private Amministratore amministratore;

	public AggiungiFarmacistaController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sesso.getItems().addAll("Maschio", "Femmina");
	}

	@Override
	public void initializeData(Farmacista farmacista) {
		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà definite
		aggiungiFarmacistaButton.disableProperty().bind((username.textProperty().isEmpty().or(password.textProperty()
				.isEmpty()
				.or(nome.textProperty().isEmpty().or(cognome.textProperty().isEmpty()).or(sesso.accessibleTextProperty()
						.isEqualTo(sesso)
						.or(cf.textProperty().isEmpty().or(dataNascita.valueProperty().isNull().or(luogoNascita
								.textProperty().isEmpty()
								.or(residenza.textProperty().isEmpty().or(telefono.textProperty().isEmpty()))))))))));
	}

	@FXML
	public void aggiungiFarmacistaAction(ActionEvent event) {
		try {

			// Variabile che conta il numero di farmacisti con il cf che si vuogliono
			// inserire
			int c = 0;
			farmacista.setUsername(username.getText());
			farmacista.setPassword(password.getText());
			farmacista.setNome(nome.getText());
			farmacista.setCognome(cognome.getText());
			farmacista.setSesso(sesso.getValue());
			farmacista.setCf(cf.getText());
			farmacista.setDataNascita(dataNascita.getValue());
			farmacista.setLuogoNascita(luogoNascita.getText());
			farmacista.setResidenza(residenza.getText());
			farmacista.setTelefono(telefono.getText());

			for (Farmacista f : utenteService.trovaTuttiFarmacisti()) {
				if (f.getUsername().equals(username.getText()) || f.getCf().equals(cf.getText())) {
					c++;
					JOptionPane.showMessageDialog(null, "Esiste già questo farmacista", "Errore",
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
				if (c == 0 && farmacista.getId() == null) {
					utenteService.creaFarmacista(farmacista);
					dispatcher.renderView("gestioneFarmacistiAmministratore", amministratore);
				}
			}

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("gestioneFarmacistiAmministratore", amministratore);
	}

}

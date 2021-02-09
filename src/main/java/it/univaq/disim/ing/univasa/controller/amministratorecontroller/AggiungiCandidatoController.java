package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AggiungiCandidatoController implements Initializable, DataInitializable<Evento> {

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
	private TextField nomeUniversita;

	@FXML
	private Button aggiungiCandidatoButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Evento evento;

	public AggiungiCandidatoController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Evento evento) {
		this.evento = evento;
		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		aggiungiCandidatoButton.disableProperty().bind((nome.textProperty().isEmpty().or(telefono.textProperty().isEmpty().or(nomeUniversita.textProperty().isEmpty().or(dataNascita.valueProperty().isNull()
				.or(cognome.textProperty().isEmpty().or(email.textProperty().isEmpty())))))));
	}

	@FXML
	public void aggiungiCandidatoAction(ActionEvent event) {
		try {
			 Candidato candidato = new Candidato();
			// Variabile che conta il numero di candidati con email che si vuole inserire
			int c = 0;
			candidato.setNome(nome.getText());
			candidato.setCognome(cognome.getText());
			candidato.setEmail(email.getText());
			candidato.setTelefono(telefono.getText());
			candidato.setNomeUniversita(nomeUniversita.getText());
			candidato.setDataNascita(dataNascita.getValue());
			System.out.println("numero candidati: " + utenteService.trovaTuttiCandidati(evento).size());
			
			for (Candidato ca : utenteService.trovaTuttiCandidati(evento)) {
				if (ca.getEmail().equals(email.getText())) {
					c++;
					JOptionPane.showMessageDialog(null, "Esiste già questo candidato", "Errore",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
				
				if (utenteService.trovaTuttiCandidati(evento).size() >= 7) {
					c++;
					JOptionPane.showMessageDialog(null, "Il numero massimo di candidati per l'evento è stato raggiunto!", " ",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}

			if (c == 0 && candidato.getId() == null) {
				utenteService.creaCandidato(candidato, evento);
				JOptionPane.showMessageDialog(null, "Candidato aggiunto con successo!", " ",
						JOptionPane.INFORMATION_MESSAGE);
				dispatcher.renderView("listaCandidatiAmministratore", evento);
			}

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaCandidatiAmministratore", evento);
	}

}

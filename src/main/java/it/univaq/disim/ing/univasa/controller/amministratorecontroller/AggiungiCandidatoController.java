package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
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
import javafx.scene.control.TextField;

public class AggiungiCandidatoController implements Initializable, DataInitializable<Evento> {

	@FXML
	private TextField nome;

	@FXML
	private TextField cognome;

	@FXML
	private TextField email;

	@FXML
	private Button aggiungiCandidatoButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Candidato candidato;

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
		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		aggiungiCandidatoButton.disableProperty().bind((nome.textProperty().isEmpty()
				.or(cognome.textProperty().isEmpty().or(email.textProperty().isEmpty()))));
	}

	@FXML
	public void aggiungiCandidatoAction(ActionEvent event) {
		try {

			// Variabile che conta il numero di candidati con email che si vuole inserire
			int c = 0;
			candidato.setNome(nome.getText());
			candidato.setCognome(cognome.getText());
			candidato.setEmail(email.getText());

			for (Candidato ca : utenteService.trovaTuttiCandidati(evento)) {
				if (ca.getEmail().equals(email.getText())) {
					c++;
					JOptionPane.showMessageDialog(null, "Esiste già questo candidato", "Errore",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}

			if (c == 0 && candidato.getId() == null) {
				utenteService.creaCandidato(candidato, evento);
				dispatcher.renderView("gestioneCandidatiAmministratore", evento);
			}

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("gestioneCandidatiAmministratore", evento);
	}

}

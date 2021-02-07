package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.TurnazioneService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.TipologiaTurno;
import it.univaq.disim.ing.univasa.domain.Turnazione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AggiungiTurnazioneController implements Initializable, DataInitializable<Turnazione> {

	@FXML
	private TextField email;

	@FXML
	private ComboBox<TipologiaTurno> fascia;

	@FXML
	private DatePicker data_turno;

	@FXML
	private TextField nome_evento;

	@FXML
	private TextField luogo;

	@FXML
	private Button salvaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private TurnazioneService turnazioneService;

	private UtenteService utenteService;

	private EventoService eventoService;

	private Turnazione turnazione;

	private Operatore operatore;

	private Evento evento;

	private Amministratore amministratore;

	public AggiungiTurnazioneController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		turnazioneService = factory.getTurnazioneService();
		utenteService = factory.getUtenteService();
		eventoService = factory.getEventoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fascia.getItems().addAll(TipologiaTurno.values());
	}

	@Override
	public void initializeData(Turnazione turnazione) {
		this.turnazione = turnazione;
		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		salvaButton.disableProperty()
				.bind((email.textProperty().isEmpty()
						.or(fascia.valueProperty().isNull().or(data_turno.valueProperty().isNull())
								.or(nome_evento.textProperty().isEmpty().or(luogo.textProperty().isEmpty())))));

	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {
			int count = 0;
			int count2 = 0;

			turnazione.setFascia(fascia.getValue());
			turnazione.setData_turno(data_turno.getValue());

			String nomeEvento = nome_evento.getText();
			String luogoEvento = luogo.getText();
			String emailOperatore = email.getText();
			Long idOperatore;
			Long idEvento;

			for (Operatore o : utenteService.trovaTuttiOperatori()) {

				if (o.getEmail().equals(emailOperatore)) {

					idOperatore = o.getId();
					turnazione.setOperatore((Operatore) utenteService.trovaUtenteDaId(idOperatore));
					count++;
				}
			}
			for (Evento e : eventoService.trovaTuttiEventi()) {

				if ((e.getNome().equals(nomeEvento)) && (e.getLuogo().equals(luogoEvento))) {

					idEvento = e.getId();
					turnazione.setEvento((Evento) eventoService.trovaEventoDaId(idEvento));
					count2++;
				}
			}
			if (turnazione.getId() == null) {
				turnazioneService.creaTurnazione(turnazione);
			} else if (count == 0) {
				JOptionPane.showMessageDialog(null, "Non c'è nessun operatore registrato con questa email",
						"ATTENZIONE", JOptionPane.WARNING_MESSAGE);
			} else if (count2 == 0) {
				JOptionPane.showMessageDialog(null, "Non c'è nessun evento programmato con questi dati", "ATTENZIONE",
						JOptionPane.WARNING_MESSAGE);
			} else if (turnazione.getEvento() == null) {
				JOptionPane.showMessageDialog(null, "Non hai specificato nessun evento", "Errore",
						JOptionPane.ERROR_MESSAGE);
			}
			dispatcher.renderView("listaTurnazioniAmministratore", amministratore);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaTurnazioniAmministratore", amministratore);
	}
}
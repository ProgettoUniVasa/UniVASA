package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.TurnazioneService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
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

	private Turnazione turnazione;

	private Operatore operatore;

	private Evento evento;

	private Amministratore amministratore;

	public AggiungiTurnazioneController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		turnazioneService = factory.getTurnazioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Turnazione turnazione) {
		this.turnazione = turnazione;
		this.email.setText(turnazione.getOperatore().getEmail());
		this.fascia.setValue(turnazione.getFascia());
		this.data_turno.setValue(turnazione.getData_turno());
		this.nome_evento.setText(turnazione.getEvento().getNome());
		this.luogo.setText(turnazione.getEvento().getLuogo());

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
			turnazione.getOperatore().setEmail(email.getText());
			turnazione.setFascia(fascia.getValue());
			turnazione.setData_turno(data_turno.getValue());
			turnazione.getEvento().setNome(nome_evento.getText());
			turnazione.getEvento().setLuogo(luogo.getText());

			if (turnazione.getId() == null) {
				turnazioneService.creaTurnazione(evento, operatore, fascia, data_turno);

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
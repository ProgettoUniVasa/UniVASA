package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AggiungiReportController implements Initializable, DataInitializable<Evento> {

	@FXML
	private TextField nomeEvento;

	@FXML
	private DatePicker dataOraInizio;

	@FXML
	private DatePicker dataOraFine;

	@FXML
	private TextField luogo;

	@FXML
	private TextField report_risultati;

	@FXML
	private TextField report_statistiche;

	@FXML
	private Button salvaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Evento evento;

	private Amministratore amministratore;

	public AggiungiReportController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Evento evento) {
		this.evento = evento;
		this.nomeEvento.setText(evento.getNome());
		this.dataOraInizio.setValue(evento.getDataOraInizio());
		this.dataOraFine.setValue(evento.getDataOraFine());
		this.luogo.setText(evento.getLuogo());
		this.report_risultati.setText(evento.getReport_risultati());
		this.report_statistiche.setText(evento.getReport_statistiche());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		salvaButton.disableProperty()
				.bind((nomeEvento.textProperty().isEmpty()
						.or((dataOraInizio.valueProperty().isNull()).or(dataOraFine.valueProperty().isNull()
								.or(luogo.textProperty().isEmpty().or(report_risultati.textProperty().isEmpty()
										.or(report_statistiche.textProperty().isEmpty())))))));
	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {
			evento.setNome(nomeEvento.getText());
			evento.setDataOraInizio(dataOraInizio.getValue());
			evento.setDataOraFine(dataOraFine.getValue());
			evento.setLuogo(luogo.getText());
			evento.setReport_risultati(report_risultati.getText());
			evento.setReport_statistiche(report_statistiche.getText());

			if (evento.getId() == null) {
				eventoService.creaEvento(evento);

			}

			dispatcher.renderView("listaReportAmministratore", amministratore);

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaReportAmministratore", amministratore);
	}
}
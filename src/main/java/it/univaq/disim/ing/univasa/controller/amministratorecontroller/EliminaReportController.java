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

public class EliminaReportController implements Initializable, DataInitializable<Evento> {

	@FXML
	private TextField nome;

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
	private Button eliminaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Evento evento;

	private Amministratore amministratore;

	public EliminaReportController() {
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
		this.nome.setText(evento.getNome());
		this.dataOraInizio.setText(evento.getDataOraInizio());
		this.dataOraFine.setValue(evento.getDataOraFine());
		this.luogo.setText(evento.getLuogo());
		this.report_risultati.setText(evento.getReport_risultati());
		this.report_statistiche.setText(evento.getReport_statistiche());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		eliminaButton.disableProperty().bind((nome.textProperty().isEmpty().or((dataOraInizio.valueProperty().isNull())
				.or(dataOraFine.valueProperty().isNull().or(luogo.textProperty().isEmpty().or(
						report_risultati.textProperty().isEmpty().or(report_statistiche.textProperty().isEmpty())))))));
	}

	@FXML
	public void eliminaAction(ActionEvent event) {
		try {
			eventoService.eliminaEvento(evento);
			dispatcher.renderView("listaReportAmministratore", amministratore);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaReportAmministratore", amministratore);
	}
}
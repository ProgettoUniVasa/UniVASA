package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
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
//aggiungere l'ora
public class EliminaReportController implements Initializable, DataInitializable<Evento> {

	@FXML
	private TextField nome;

	@FXML
	private DatePicker dataInizio;

	@FXML
	private DatePicker dataFine;

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
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Evento evento) {
		this.evento = evento;
		this.nome.setText(evento.getNome());
		this.dataInizio.setValue(evento.getDataInizio());
		this.dataFine.setValue(evento.getDataFine());
		this.luogo.setText(evento.getLuogo());
		this.report_risultati.setText(evento.getReport_risultati());
		this.report_statistiche.setText(evento.getReport_statistiche());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		eliminaButton.disableProperty().bind((nome.textProperty().isEmpty().or((dataInizio.valueProperty().isNull())
				.or(dataFine.valueProperty().isNull().or(luogo.textProperty().isEmpty().or(
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
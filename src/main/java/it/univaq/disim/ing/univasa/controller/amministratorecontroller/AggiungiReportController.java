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

public class AggiungiReportController implements Initializable, DataInitializable<Evento> {

	@FXML
	private TextField nomeEvento;

	@FXML
	private TextField regolamentoEvento;

	@FXML
	private DatePicker dataInizio;

	@FXML
	private DatePicker dataFine;

	@FXML
	private TextField luogo;

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
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Evento evento) {
		this.evento = evento;
		this.nomeEvento.setText(evento.getNome());
		this.regolamentoEvento.setText(evento.getRegolamento());
		this.dataInizio.setValue(evento.getDataInizio());
		this.dataFine.setValue(evento.getDataFine());
		this.luogo.setText(evento.getLuogo());
		this.report_statistiche.setText(evento.getReport_statistiche());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		salvaButton.disableProperty()
				.bind((nomeEvento.textProperty().isEmpty().or(regolamentoEvento.textProperty().isEmpty())
						.or((dataInizio.valueProperty().isNull())
								.or(dataFine.valueProperty().isNull().or(luogo.textProperty().isEmpty())))));
	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {
			evento.setNome(nomeEvento.getText());
			evento.setRegolamento(regolamentoEvento.getText());
			evento.setDataInizio(dataInizio.getValue());
			evento.setDataFine(dataFine.getValue());
			evento.setLuogo(luogo.getText());
			evento.setReport_statistiche(report_statistiche.getText());

			if (evento.getId() != null) {
				eventoService.modificaStatistiche(evento);
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
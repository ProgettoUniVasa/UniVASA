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
public class AggiungiEventoController implements Initializable, DataInitializable<Evento> {

	@FXML
	private TextField nome;

	@FXML
	private TextField regolamento;

	@FXML
	private DatePicker dataInizio;

	@FXML
	private DatePicker dataFine;

	@FXML
	private TextField luogo;

	@FXML
	private TextField numero_preferenze_esprimibili;

	@FXML
	private Button salvaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Evento evento;

	private Amministratore amministratore;

	public AggiungiEventoController() {
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
		this.regolamento.setText(evento.getRegolamento());
		this.dataInizio.setValue(evento.getDataInizio());
		this.dataFine.setValue(evento.getDataFine());
		this.luogo.setText(evento.getLuogo());
		this.numero_preferenze_esprimibili.setText("" + evento.getNumero_preferenze_esprimibili());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		salvaButton.disableProperty()
				.bind((nome.textProperty().isEmpty()
						.or(regolamento.textProperty().isEmpty().or(dataInizio.valueProperty().isNull())
								.or(dataFine.valueProperty().isNull().or(luogo.textProperty().isEmpty()
										.or((numero_preferenze_esprimibili.textProperty().isEmpty())))))));
	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {
			evento.setNome(nome.getText());
			evento.setRegolamento(regolamento.getText());
			evento.setDataInizio(dataInizio.getValue());
			evento.setDataFine(dataFine.getValue());
			evento.setLuogo(luogo.getText());
			evento.setNumero_preferenze_esprimibili(Integer.parseInt(numero_preferenze_esprimibili.getText()));

			if (evento.getId() == null) {
				eventoService.creaEvento(evento);

			}

			dispatcher.renderView("listaEventiAmministratore", amministratore);

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaEventiAmministratore", amministratore);
	}
}
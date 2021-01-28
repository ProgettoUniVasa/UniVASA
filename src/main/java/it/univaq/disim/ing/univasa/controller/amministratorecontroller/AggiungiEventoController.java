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

public class AggiungiEventoController implements Initializable, DataInitializable<Evento> {

	@FXML
	private TextField nome;

	@FXML
	private TextField regolamento;

	@FXML
	private DatePicker dataOraInizio;

	@FXML
	private DatePicker dataOraFine;

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
		this.regolamento.setText(evento.getRegolamento());
		this.dataOraInizio.setValue(evento.getDataOraInizio()); // Con LocalDate va
		this.dataOraFine.setValue(evento.getDataOraFine());
		this.luogo.setText(evento.getLuogo());
		this.numero_preferenze_esprimibili.setText("" + evento.getNumero_preferenze_esprimibili());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		salvaButton.disableProperty()
				.bind((nome.textProperty().isEmpty()
						.or(regolamento.textProperty().isEmpty().or(dataOraInizio.valueProperty().isNull())
								.or(dataOraFine.valueProperty().isNull().or(luogo.textProperty().isEmpty()
										.or((numero_preferenze_esprimibili.textProperty().isEmpty())))))));
	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {
			evento.setNome(nome.getText());
			evento.setRegolamento(regolamento.getText());
			evento.setDataOraInizio(dataOraInizio.getValue());
			evento.setDataOraFine(dataOraFine.getValue());
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
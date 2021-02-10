package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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

public class AggiungiEventoController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private TextField nome;

	@FXML
	private TextField regolamento;

	@FXML
	private DatePicker dataInizio;

	@FXML
	private DatePicker dataFine;

	@FXML
	private TextField oraInizio;

	@FXML
	private TextField oraFine;

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
	public void initializeData(Amministratore amministratore) {
		this.amministratore = amministratore;

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		salvaButton.disableProperty()
				.bind((nome.textProperty().isEmpty()
						.or(regolamento.textProperty().isEmpty().or(dataInizio.valueProperty().isNull())
								.or(dataFine.valueProperty().isNull().or(oraInizio.textProperty().isEmpty()
										.or(oraFine.textProperty().isEmpty().or(luogo.textProperty().isEmpty()
												.or((numero_preferenze_esprimibili.textProperty().isEmpty())))))))));
	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {
			if (!oraFine.getText().matches("[0-2][0-9]:[0-5][0-9]$")
					|| !oraInizio.getText().matches("[0-2][0-9]:[0-5][0-9]$")) {
				JOptionPane.showMessageDialog(null, " Inserire un orario corretto!", "ATTENZIONE",
						JOptionPane.WARNING_MESSAGE);
			} else {
				Evento evento = new Evento();
				evento.setNome(nome.getText());
				evento.setRegolamento(regolamento.getText());
				evento.setDataInizio(dataInizio.getValue());
				evento.setDataFine(dataFine.getValue());
				evento.setOraInizio(oraInizio.getText());
				evento.setOraFine(oraFine.getText());
				evento.setLuogo(luogo.getText());
				evento.setNumero_preferenze_esprimibili(Integer.parseInt(numero_preferenze_esprimibili.getText()));

				eventoService.creaEvento(evento);
				JOptionPane.showMessageDialog(null, "Evento aggiunto con successo! Ricordati di inserire i candidati",
						" ", JOptionPane.INFORMATION_MESSAGE);
				dispatcher.renderView("listaEventiAmministratore", amministratore);
			}
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaEventiAmministratore", amministratore);
	}
}
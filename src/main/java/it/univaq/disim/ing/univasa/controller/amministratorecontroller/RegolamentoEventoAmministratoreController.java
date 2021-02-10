package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import it.univaq.disim.ing.univasa.view.ViewException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegolamentoEventoAmministratoreController implements Initializable, DataInitializable<Evento> {

	@FXML
	private TextField nome;

	@FXML
	private TextField regolamento;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private UtenteService utenteService;

	private Evento evento;

	private Amministratore amministratore;

	public RegolamentoEventoAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Evento evento) {
		this.evento = evento;
		this.nome.setText(evento.getNome());
		this.regolamento.setText(evento.getRegolamento());
		this.nome.setEditable(false);
		this.regolamento.setEditable(false);
	}

	@FXML
	public void indietroAction(ActionEvent event) throws ViewException {
		dispatcher.renderView("listaEventiAmministratore", amministratore);
	}
}

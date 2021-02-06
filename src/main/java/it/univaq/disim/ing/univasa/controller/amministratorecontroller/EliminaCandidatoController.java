package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EliminaCandidatoController implements Initializable, DataInitializable<Candidato> {

	@FXML
	private TextField nome;

	@FXML
	private TextField cognome;

	@FXML
	private TextField email;

	@FXML
	private TextField telefono;

	@FXML
	private TextField nome_università;

	@FXML
	private TextField dipartimento;

	@FXML
	private Button eliminaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Candidato candidato;

	private Amministratore amministratore;

	public EliminaCandidatoController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Candidato candidato) {
		this.candidato = candidato;
		this.nome.setText(candidato.getNome());
		this.cognome.setText(candidato.getCognome());
		this.email.setText(candidato.getEmail());
	}

	@FXML
	public void eliminaAction(ActionEvent event) {

		try {

			utenteService.eliminaCandidato(candidato);
			dispatcher.renderView("listaCandidatiAmministratore", candidato.getEvento());

		} catch (BusinessException e) {
			//e.printStackTrace();
		}

	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaCandidatiAmministratore", candidato.getEvento());
	}

}
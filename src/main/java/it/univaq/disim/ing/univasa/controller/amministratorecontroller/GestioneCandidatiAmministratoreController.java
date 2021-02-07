package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Professione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class GestioneCandidatiAmministratoreController implements Initializable, DataInitializable<Evento> {

	@FXML
	private Label candidatiLabel;

	@FXML
	private TableView<Candidato> candidatiTable;

	@FXML
	private TableColumn<Candidato, String> nomeTableColumn;

	@FXML
	private TableColumn<Candidato, String> cognomeTableColumn;

	@FXML
	private TableColumn<Candidato, String> emailTableColumn;

	@FXML
	private TableColumn<Candidato, String> telefonoTableColumn;

	@FXML
	private TableColumn<Candidato, LocalDate> dataNascitaTableColumn;

	@FXML
	private TableColumn<Candidato, Professione> professioneTableColumn;

	@FXML
	private TableColumn<Candidato, String> nomeUniversitàTableColumn;

	@FXML
	private TableColumn<Candidato, String> dipartimentoTableColumn;

	@FXML
	private TableColumn<Candidato, Button> eliminaTableColumn;

	@FXML
	private Button aggiungiCandidatoButton;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private UtenteService utenteService;

	private Candidato candidato;

	private Evento evento;

	private Amministratore amministratore;

	public GestioneCandidatiAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		;

		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Candidato, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Candidato, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaCandidato", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});

	}

	@Override
	public void initializeData(Evento evento) {
		try {
			this.evento = evento;
			List<Candidato> candidati = eventoService.visualizzaCandidati(evento);
			ObservableList<Candidato> candidatiData = FXCollections.observableArrayList(candidati);
			candidatiTable.setItems(candidatiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiCandidatoAction(ActionEvent event) {
		dispatcher.renderView("aggiungiCandidato", evento);
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("listaEventiAmministratore", amministratore);
	}
}

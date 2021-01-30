package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaPrenotatiAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private Label prenotatiLabel;

	@FXML
	private TableView<Elettore> prenotatiTable;

	@FXML
	private TableColumn<Elettore, String> nomeTableColumn;

	@FXML
	private TableColumn<Elettore, String> cognomeTableColumn;

	@FXML
	private TableColumn<Elettore, String> emailTableColumn;

	@FXML
	private TableColumn<Elettore, String> telefonoTableColumn;

	@FXML
	private TableColumn<Elettore, String> matricolaTableColumn;

	@FXML
	private TableColumn<Elettore, Button> eliminaTableColumn;

	@FXML
	private Button aggiungiElettoreButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Elettore elettore;

	private Evento evento;

	private Amministratore amministratore;

	public ListaPrenotatiAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		telefonoTableColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		matricolaTableColumn.setCellValueFactory(new PropertyValueFactory<>("matricola"));

	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			List<Elettore> elettori = utenteService.gestionePrenotazioni(evento);
			ObservableList<Elettore> elettoriData = FXCollections.observableArrayList(elettori);
			prenotatiTable.setItems(elettoriData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("listaEventiAmministratore", amministratore);
	}
}

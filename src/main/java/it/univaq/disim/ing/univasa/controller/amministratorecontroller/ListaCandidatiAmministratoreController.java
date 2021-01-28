package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
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

public class ListaCandidatiAmministratoreController implements Initializable, DataInitializable<Amministratore> {

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
	private TableColumn<Candidato, String> usernameTableColumn;

	@FXML
	private TableColumn<Candidato, String> passwordTableColumn;

	@FXML
	private TableColumn<Candidato, String> telefonoTableColumn;

	@FXML
	private TableColumn<Candidato, LocalDate> dataNascitaTableColumn;

	@FXML
	private TableColumn<Candidato, Professione> professioneTableColumn;

	@FXML
	private TableColumn<Candidato, String> nome_universitàTableColumn;

	@FXML
	private TableColumn<Candidato, String> dipartimentoTableColumn;
	
	//voti ricevuti, evento

	@FXML
	private Button aggiungiCandidatoButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Candidato candidato;

	@FXML
	private TableColumn<Candidato, Button> azioniTableColumn;

	@FXML
	private TableColumn<Candidato, Button> modificaTableColumn;

	@FXML
	private TableColumn<Candidato, Button> eliminaTableColumn;

	public ListaCandidatiAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		passwordTableColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		telefonoTableColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
		dataNascitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));
		professioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("professione"));
		nome_universitàTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome_università"));
		dipartimentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("dipartimento"));

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
	public void initializeData(Amministratore amministratore) {
		try {
			List<Candidato> candidato = utenteService.trovaTuttiCandidati();
			ObservableList<Candidato> candidatiData = FXCollections.observableArrayList(candidati);

			candidatiTable.setItems(candidatiData);

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiCandidatoAction(ActionEvent event) {
		Candidato candidato = new Candidato();
		dispatcher.renderView("aggiungiCandidato", candidato);
	}
}

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
import it.univaq.disim.ing.univasa.domain.Farmacista;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class GestioneCandidatiAmministratoreController implements Initializable, DataInitializable<Amministratore> {

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

	
	@FXML
	private TableColumn<Candidato, Button> modificaTableColumn;

	@FXML
	private TableColumn<Candidato, Button> eliminaTableColumn;

	@FXML
	private Button aggiungiCandidatoButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Candidato candidato;

	public GestioneCandidatiAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		cfTableColumn.setCellValueFactory(new PropertyValueFactory<>("cf"));
		residenzaTableColumn.setCellValueFactory(new PropertyValueFactory<>("residenza"));
		telefonoTableColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));

		modificaTableColumn.setStyle("-fx-alignment: CENTER;");
		modificaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Farmacista, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Farmacista, Button> param) {
						final Button modificaButton = new Button("Modifica");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("modificaFarmacista", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});

		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Farmacista, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Farmacista, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaFarmacisti", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});

	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			List<Farmacista> farmacisti = utenteService.trovaTuttiFarmacisti();
			ObservableList<Farmacista> farmacistiData = FXCollections.observableArrayList(farmacisti);
			farmacistiTable.setItems(farmacistiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiFarmacistaAction(ActionEvent event) {
		dispatcher.renderView("aggiungiFarmacista", farmacista);
	}

}

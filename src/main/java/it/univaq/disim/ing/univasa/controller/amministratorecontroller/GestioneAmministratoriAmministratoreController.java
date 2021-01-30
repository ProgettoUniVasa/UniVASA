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

public class GestioneAmministratoriAmministratoreController
		implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private TableView<Amministratore> amministratoriTable;

	@FXML
	private TableColumn<Amministratore, String> nomeTableColumn;

	@FXML
	private TableColumn<Amministratore, String> cognomeTableColumn;

	@FXML
	private TableColumn<Amministratore, String> emailTableColumn;

	@FXML
	private TableColumn<Amministratore, String> telefonoTableColumn;

	@FXML
	private TableColumn<Amministratore, LocalDate> dataNascitaTableColumn;

	@FXML
	private TableColumn<Amministratore, Professione> professioneTableColumn;

	@FXML
	private TableColumn<Amministratore, String> nome_universitàTableColumn;

	@FXML
	private TableColumn<Amministratore, String> dipartimentoTableColumn;

	@FXML
	private TableColumn<Amministratore, Button> modificaTableColumn;

	@FXML
	private TableColumn<Amministratore, Button> eliminaTableColumn;

	@FXML
	private Button aggiungiAmministratoreButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Amministratore amministratore;

	public GestioneAmministratoriAmministratoreController() {
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
		dataNascitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));
		professioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("professione"));
		nome_universitàTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome_università"));
		dipartimentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("dipartimento"));

		modificaTableColumn.setStyle("-fx-alignment: CENTER;");
		modificaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amministratore, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Amministratore, Button> param) {
						final Button modificaButton = new Button("Modifica");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("modificaAmministratore", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});

		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Amministratore, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Amministratore, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaAmministratore", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});

	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			List<Amministratore> amministratori = utenteService.trovaTuttiAmministratori();
			ObservableList<Amministratore> amministratoriData = FXCollections.observableArrayList(amministratori);
			amministratoriTable.setItems(amministratoriData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiAmministratoreAction(ActionEvent event) {
		dispatcher.renderView("aggiungiAmministratore", amministratore);
	}
	
	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("dashboardAmministratore", amministratore);
	}

}

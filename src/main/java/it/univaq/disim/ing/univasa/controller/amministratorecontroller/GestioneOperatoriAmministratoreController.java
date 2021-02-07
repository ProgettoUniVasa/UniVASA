package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Operatore;
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

public class GestioneOperatoriAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private TableView<Operatore> operatoriTable;

	@FXML
	private TableColumn<Operatore, String> nomeTableColumn;

	@FXML
	private TableColumn<Operatore, String> cognomeTableColumn;

	@FXML
	private TableColumn<Operatore, String> emailTableColumn;

	@FXML
	private TableColumn<Operatore, String> telefonoTableColumn;

	@FXML
	private TableColumn<Operatore, LocalDate> dataNascitaTableColumn;

	@FXML
	private TableColumn<Operatore, Professione> professioneTableColumn;

	@FXML
	private TableColumn<Operatore, String> nome_universitàTableColumn;

	@FXML
	private TableColumn<Operatore, String> dipartimentoTableColumn;

	@FXML
	private TableColumn<Operatore, Button> modificaTableColumn;

	@FXML
	private TableColumn<Operatore, Button> eliminaTableColumn;

	@FXML
	private Button aggiungiOperatoreButton;

	@FXML
	private Button indietroButton;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	private Operatore operatore;

	private Amministratore amministratore;

	public GestioneOperatoriAmministratoreController() {
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
		dataNascitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("data_nascita"));
		professioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("professione"));
		nome_universitàTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome_università"));
		dipartimentoTableColumn.setCellValueFactory(new PropertyValueFactory<>("dipartimento"));

		modificaTableColumn.setStyle("-fx-alignment: CENTER;");
		modificaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Operatore, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Operatore, Button> param) {
						final Button modificaButton = new Button("Modifica");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("modificaOperatore", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});

		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Operatore, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Operatore, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaOperatore", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});

	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			this.amministratore = amministratore;
			List<Operatore> operatori = utenteService.trovaTuttiOperatori();
			ObservableList<Operatore> operatoriData = FXCollections.observableArrayList(operatori);
			operatoriTable.setItems(operatoriData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiOperatoreAction(ActionEvent event) {
		dispatcher.renderView("aggiungiOperatore", operatore);
	}

	@FXML
	public void indietroAction(ActionEvent event) {
		dispatcher.renderView("dashboardAmministratore", amministratore);
	}
}

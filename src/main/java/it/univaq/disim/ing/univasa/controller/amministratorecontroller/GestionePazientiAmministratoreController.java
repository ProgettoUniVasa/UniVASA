package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Paziente;
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

public class GestionePazientiAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private TableView<Paziente> pazientiTable;

	@FXML
	private TableColumn<Paziente, String> nomeTableColumn;

	@FXML
	private TableColumn<Paziente, String> cognomeTableColumn;

	@FXML
	private TableColumn<Paziente, String> cfTableColumn;

	@FXML
	private TableColumn<Paziente, String> luogoNascitaTableColumn;

	@FXML
	private TableColumn<Paziente, String> residenzaTableColumn;

	@FXML
	private TableColumn<Paziente, String> telefonoTableColumn;

	@FXML
	private TableColumn<Paziente, Button> modificaTableColumn;

	@FXML
	private TableColumn<Paziente, Button> eliminaTableColumn;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	public GestionePazientiAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		cfTableColumn.setCellValueFactory(new PropertyValueFactory<>("cf"));
		luogoNascitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("luogoNascita"));
		residenzaTableColumn.setCellValueFactory(new PropertyValueFactory<>("residenza"));
		telefonoTableColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));

		modificaTableColumn.setStyle("-fx-alignment: CENTER;");
		modificaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Paziente, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Paziente, Button> param) {
						final Button modificaButton = new Button("Modifica");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("modificaPaziente", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});

		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Paziente, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Paziente, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaPazienti", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});

	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			List<Paziente> pazienti = utenteService.trovaTuttiPazienti();
			ObservableList<Paziente> pazientiData = FXCollections.observableArrayList(pazienti);
			pazientiTable.setItems(pazientiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

}
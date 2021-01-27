package it.univaq.disim.oop.pharma.controller.amministratorecontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.UtenteService;
import it.univaq.disim.oop.pharma.controller.DataInitializable;
import it.univaq.disim.oop.pharma.domain.Amministratore;
import it.univaq.disim.oop.pharma.domain.Medico;
import it.univaq.disim.oop.pharma.domain.TipologiaMedico;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
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

public class GestioneMediciAmministratoreController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private TableView<Medico> mediciTable;

	@FXML
	private TableColumn<Medico, String> nomeTableColumn;

	@FXML
	private TableColumn<Medico, String> cognomeTableColumn;

	@FXML
	private TableColumn<Medico, String> cfTableColumn;

	@FXML
	private TableColumn<Medico, String> residenzaTableColumn;

	@FXML
	private TableColumn<Medico, String> telefonoTableColumn;

	@FXML
	private TableColumn<Medico, String> codiceAlboMediciTableColumn;

	@FXML
	private TableColumn<Medico, TipologiaMedico> tipologiaTableColumn;

	@FXML
	private TableColumn<Medico, Button> modificaTableColumn;

	@FXML
	private TableColumn<Medico, Button> eliminaTableColumn;

	private ViewDispatcher dispatcher;

	private UtenteService utenteService;

	public GestioneMediciAmministratoreController() {
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
		codiceAlboMediciTableColumn.setCellValueFactory(new PropertyValueFactory<>("codiceAlboMedici"));
		tipologiaTableColumn.setCellValueFactory(new PropertyValueFactory<>("tipologia"));

		modificaTableColumn.setStyle("-fx-alignment: CENTER;");
		modificaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Medico, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Medico, Button> param) {
						final Button modificaButton = new Button("Modifica");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("modificaMedico", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});

		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Medico, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Medico, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaMedici", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});

	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			List<Medico> medici = utenteService.trovaTuttiMedici();
			ObservableList<Medico> mediciData = FXCollections.observableArrayList(medici);
			mediciTable.setItems(mediciData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

}

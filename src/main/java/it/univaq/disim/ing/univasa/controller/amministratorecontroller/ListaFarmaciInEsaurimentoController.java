package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.FarmacoService;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Farmaco;
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

public class ListaFarmaciInEsaurimentoController implements Initializable, DataInitializable<Amministratore> {

	@FXML
	private Label farmaciLabel;

	@FXML
	private TableView<Farmaco> farmaciTable;

	@FXML
	private TableColumn<Farmaco, String> nomeTableColumn;

	@FXML
	private TableColumn<Farmaco, String> principioAttivoTableColumn;

	@FXML
	private TableColumn<Farmaco, String> produttoreTableColumn;

	@FXML
	private TableColumn<Farmaco, LocalDate> scadenzaTableColumn;

	@FXML
	private TableColumn<Farmaco, Double> costoTableColumn;

	@FXML
	private TableColumn<Farmaco, Integer> quantitaDisponibileTableColumn;

	@FXML
	private TableColumn<Farmaco, Button> azioniTableColumn;

	private ViewDispatcher dispatcher;

	private FarmacoService farmacoService;

	public ListaFarmaciInEsaurimentoController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		farmacoService = factory.getFarmacoService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setStyle("-fx-alignment: CENTER;");
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		principioAttivoTableColumn.setStyle("-fx-alignment: CENTER;");
		principioAttivoTableColumn.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));

		produttoreTableColumn.setStyle("-fx-alignment: CENTER;");
		produttoreTableColumn.setCellValueFactory(new PropertyValueFactory<>("produttore"));

		scadenzaTableColumn.setStyle("-fx-alignment: CENTER;");
		scadenzaTableColumn.setCellValueFactory(new PropertyValueFactory<>("scadenza"));

		costoTableColumn.setStyle("-fx-alignment: CENTER;");
		costoTableColumn.setCellValueFactory(new PropertyValueFactory<>("costo"));

		quantitaDisponibileTableColumn.setStyle("-fx-alignment: CENTER;");
		quantitaDisponibileTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantitaDisponibile"));

		azioniTableColumn.setStyle("-fx-alignment: CENTER;");
		azioniTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Farmaco, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Farmaco, Button> param) {
						final Button modificaButton = new Button("Modifica" + "\n" + " Quantità");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("modificaQuantitaMinimaFarmaco", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});
	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			List<Farmaco> farmaci = farmacoService.farmaciInEsaurimento();
			ObservableList<Farmaco> farmaciData = FXCollections.observableArrayList(farmaci);
			farmaciTable.setItems(farmaciData);

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

}

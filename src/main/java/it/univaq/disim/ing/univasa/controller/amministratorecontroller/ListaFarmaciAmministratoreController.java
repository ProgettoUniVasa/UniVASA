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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ListaFarmaciAmministratoreController implements Initializable, DataInitializable<Amministratore> {

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

	@FXML
	private TableColumn<Farmaco, Button> eliminaTableColumn;

	@FXML
	private Button aggiungiFarmacoButton;

	private ViewDispatcher dispatcher;

	private FarmacoService farmacoService;

	public ListaFarmaciAmministratoreController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		farmacoService = factory.getFarmacoService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		principioAttivoTableColumn.setCellValueFactory(new PropertyValueFactory<>("principioAttivo"));
		produttoreTableColumn.setCellValueFactory(new PropertyValueFactory<>("produttore"));
		scadenzaTableColumn.setCellValueFactory(new PropertyValueFactory<>("scadenza"));
		costoTableColumn.setCellValueFactory(new PropertyValueFactory<>("costo"));
		quantitaDisponibileTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantitaDisponibile"));

		azioniTableColumn.setStyle("-fx-alignment: CENTER;");
		azioniTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Farmaco, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Farmaco, Button> param) {
						final Button modificaButton = new Button("Modifica");
						modificaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("modificaFarmaco", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(modificaButton);
					}
				});

		eliminaTableColumn.setStyle("-fx-alignment: CENTER;");
		eliminaTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Farmaco, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Farmaco, Button> param) {
						final Button eliminaButton = new Button("Elimina");
						eliminaButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("eliminaFarmaco", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(eliminaButton);
					}
				});

	}

	@Override
	public void initializeData(Amministratore amministratore) {
		try {
			List<Farmaco> farmaci = farmacoService.trovaTuttiFarmaci();
			ObservableList<Farmaco> farmaciData = FXCollections.observableArrayList(farmaci);

			nomeTableColumn.setCellFactory(new Callback<TableColumn<Farmaco, String>, TableCell<Farmaco, String>>() {
				public TableCell<Farmaco, String> call(TableColumn<Farmaco, String> param) {
					return new TableCell<Farmaco, String>() {
						@Override
						public void updateItem(String item, boolean empty) {
							super.updateItem(item, empty);
							if (!isEmpty()) {
								try {
									// Per ogni farmaco in esaurimento si colora il nome di rosso
									for (Farmaco f : farmacoService.farmaciInEsaurimento()) {
										if (item.contains("" + f.getNome())) {
											this.setTextFill(Color.RED);
											break;
										}
									}
								} catch (BusinessException e) {
									e.printStackTrace();
								}
								setText(item);
							}
						}
					};
				}
			});

			farmaciTable.setItems(farmaciData);

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiFarmacoAction(ActionEvent event) {
		Farmaco farmaco = new Farmaco();
		dispatcher.renderView("aggiungiFarmaco", farmaco);
	}
}

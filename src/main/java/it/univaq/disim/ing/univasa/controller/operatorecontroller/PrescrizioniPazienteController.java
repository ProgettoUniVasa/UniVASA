package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Paziente;
import it.univaq.disim.ing.univasa.domain.Prescrizione;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class PrescrizioniPazienteController implements Initializable, DataInitializable<Paziente> {

	@FXML
	private TextField cercaPrescrizione;

	@FXML
	private Label ricercaErrorLabel;

	@FXML
	private Button cercaButton;

	@FXML
	private TableView<Prescrizione> prescrizioniTable;

	@FXML
	private TableColumn<Prescrizione, LocalDate> dataTableColumn;

	@FXML
	private TableColumn<Prescrizione, String> numeroTableColumn;

	@FXML
	private TableColumn<Prescrizione, Boolean> firmaTableColumn;

	@FXML
	private TableColumn<Prescrizione, Double> costoPrescrizioneTableColumn;

	@FXML
	private TableColumn<Prescrizione, Button> azioniTableColumn;

	@FXML
	private TableColumn<Prescrizione, String> evasioneTableColumn;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private Prescrizione prescrizione;

	private Paziente paziente;

	public PrescrizioniPazienteController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		dataTableColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
		numeroTableColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
		firmaTableColumn.setCellValueFactory(new PropertyValueFactory<>("firma"));
		costoPrescrizioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("costoPrescrizione"));
		evasioneTableColumn.setStyle("-fx-alignment: CENTER;");
		evasioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("evasione"));

		evasioneTableColumn
				.setCellFactory(new Callback<TableColumn<Prescrizione, String>, TableCell<Prescrizione, String>>() {
					public TableCell<Prescrizione, String> call(TableColumn<Prescrizione, String> param) {
						return new TableCell<Prescrizione, String>() {
							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (!isEmpty()) {
									if (item.contains("SI")) {
										this.setTextFill(Color.GREEN);
									} else {
										this.setTextFill(Color.RED);
									}
									setText(item);
								}
							}
						};
					}
				});

		azioniTableColumn.setStyle("-fx-alignment: CENTER;");
		azioniTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, Button>, ObservableValue<Button>>() {
					@Override
					public ObservableValue<Button> call(CellDataFeatures<Prescrizione, Button> param) {
						final Button farmaciButton = new Button("Farmaci");
						farmaciButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("farmaciPaziente", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(farmaciButton);
					}
				});
	}

	@Override
	public void initializeData(Paziente paziente) {
		try {
			this.paziente = paziente;
			List<Prescrizione> prescrizioni = prescrizioneService.trovaPrescrizioniPaziente(paziente);
			ObservableList<Prescrizione> prescrizioniData = FXCollections.observableArrayList(prescrizioni);
			prescrizioniTable.setItems(prescrizioniData);

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	private void cercaAction(ActionEvent event) {
		try {
			String prescrizioneCercata = cercaPrescrizione.getText();

			for (Prescrizione p : prescrizioneService.trovaPrescrizioniPaziente(paziente)) {
				if (p.getNumero().equals(prescrizioneCercata)) {

					prescrizione = p;

					dispatcher.renderView("prescrizioneCercata", prescrizione);
				} else {
					ricercaErrorLabel.setText("La prescrizione cercata non esiste!");
				}

			}
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

}

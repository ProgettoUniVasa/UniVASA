package it.univaq.disim.ing.univasa.controller.farmacistacontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Farmacista;
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

public class PrescrizioniFarmacistaController implements Initializable, DataInitializable<Farmacista> {

	@FXML
	private TableView<Prescrizione> prescrizioniTable;

	@FXML
	private TableColumn<Prescrizione, String> numeroTableColumn;

	@FXML
	private TableColumn<Prescrizione, LocalDate> dataTableColumn;

	@FXML
	private TableColumn<Prescrizione, String> firmaTableColumn;

	@FXML
	private TableColumn<Prescrizione, Button> evasioniTableColumn;

	@FXML
	private TableColumn<Prescrizione, String> evasaTableColumn;

	@FXML
	private TextField cercaPrescrizione;

	@FXML
	private Label ricercaErrorLabel;

	@FXML
	private Button cercaButton;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private Prescrizione prescrizione;

	public PrescrizioniFarmacistaController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		numeroTableColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
		dataTableColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
		firmaTableColumn.setCellValueFactory(new PropertyValueFactory<>("firma"));
		evasaTableColumn.setStyle("-fx-alignment: CENTER;");
		evasaTableColumn.setCellValueFactory(new PropertyValueFactory<>("evasione"));

		evasaTableColumn
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

		evasioniTableColumn.setStyle("-fx-alignment: CENTER;");
		evasioniTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prescrizione, Button>, ObservableValue<Button>>() {

					@Override
					public ObservableValue<Button> call(CellDataFeatures<Prescrizione, Button> param) {
						final Button prescrizioniButton = new Button("Evadi Prescrizione");
						prescrizioniButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								dispatcher.renderView("evadiPrescrizione", param.getValue());
							}
						});
						return new SimpleObjectProperty<Button>(prescrizioniButton);
					}
				});

	}

	@Override
	public void initializeData(Farmacista farmacista) {
		try {

			List<Prescrizione> prescrizioni = prescrizioneService.trovaTuttePrescrizioni();
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

			for (Prescrizione p : prescrizioneService.trovaTuttePrescrizioni()) {
				if (p.getNumero().equals(prescrizioneCercata)) {

					prescrizione = p;

					dispatcher.renderView("cercaPrescrizioneFarmacista", prescrizione);
				} else {
					ricercaErrorLabel.setText("La prescrizione cercata non esiste!");
				}

			}
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

}
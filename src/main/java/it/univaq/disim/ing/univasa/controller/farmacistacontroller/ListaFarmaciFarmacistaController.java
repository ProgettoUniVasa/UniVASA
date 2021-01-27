package it.univaq.disim.ing.univasa.controller.farmacistacontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.FarmacoService;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Farmacista;
import it.univaq.disim.ing.univasa.domain.Farmaco;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaFarmaciFarmacistaController implements Initializable, DataInitializable<Farmacista> {

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

	private ViewDispatcher dispatcher;

	private FarmacoService farmacoService;

	public ListaFarmaciFarmacistaController() {
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
	}

	@Override
	public void initializeData(Farmacista farmacista) {
		try {
			List<Farmaco> farmaci = farmacoService.trovaTuttiFarmaci();
			ObservableList<Farmaco> farmaciData = FXCollections.observableArrayList(farmaci);
			farmaciTable.setItems(farmaciData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
}

package it.univaq.disim.oop.pharma.controller.medicocontroller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.FarmacoService;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.business.PrescrizioneService;
import it.univaq.disim.oop.pharma.controller.DataInitializable;
import it.univaq.disim.oop.pharma.domain.Farmaco;
import it.univaq.disim.oop.pharma.domain.FarmacoPrescritto;
import it.univaq.disim.oop.pharma.domain.Medico;
import it.univaq.disim.oop.pharma.domain.Prescrizione;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class PrescriviFarmaciController implements Initializable, DataInitializable<Prescrizione> {

	@FXML
	private ComboBox<String> nome;

	@FXML
	private TextField quantitaPrescritta;

	@FXML
	private TableView<FarmacoPrescritto> farmaciPrescrittiTable;

	@FXML
	private TableColumn<FarmacoPrescritto, String> nomeTableColumn;

	@FXML
	private TableColumn<FarmacoPrescritto, Integer> quantitaTableColumn;

	@FXML
	private Button aggiungiFarmacoPrescrittoButton;

	@FXML
	private Button terminaPrescrizioneFarmaciButton;

	@FXML
	private Button annullaPrescrizioneFarmaciButton;

	private ViewDispatcher dispatcher;

	private FarmacoService farmacoService;

	private Prescrizione prescrizione;

	private PrescrizioneService prescrizioneService;

	private static List<FarmacoPrescritto> lista = new ArrayList<>();

	public PrescriviFarmaciController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		farmacoService = factory.getFarmacoService();
		prescrizioneService = factory.getPrescrizioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nomeTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<FarmacoPrescritto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<FarmacoPrescritto, String> param) {
						return new SimpleStringProperty(param.getValue().getFarmaco().getNome());
					}
				});

		quantitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantita"));

		try {

			nome.getItems().addAll(farmacoService.nomiFarmaci());

		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initializeData(Prescrizione prescrizione) {

		List<FarmacoPrescritto> farmaciPrescritti;
		try {

			this.prescrizione = prescrizione;
			Medico medico = prescrizione.getMedico();
			farmaciPrescritti = prescrizioneService.trovaFarmaciPrescritti(prescrizione);
			ObservableList<FarmacoPrescritto> farmaciData = FXCollections.observableArrayList(farmaciPrescritti);
			farmaciPrescrittiTable.setItems(farmaciData);

		} catch (BusinessException e) {
			e.printStackTrace();
			dispatcher.renderError(e);
		}
		this.quantitaPrescritta.setText("0");

		if (prescrizione.getFarmaciPrescritti().isEmpty())
			this.terminaPrescrizioneFarmaciButton.disableProperty();

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		this.aggiungiFarmacoPrescrittoButton.disableProperty()
				.bind(quantitaPrescritta.textProperty().isEqualTo("0").or(quantitaPrescritta.textProperty().isEmpty()));
	}

	@FXML
	public void aggiungiFarmacoPrescrittoAction(ActionEvent event) {
		try {
			FarmacoPrescritto nuovoFarmacoPrescritto = new FarmacoPrescritto();
			nuovoFarmacoPrescritto.setPrescrizione(prescrizione);

			String nomeFarmaco = nome.getValue();
			Integer idFarmaco;
			for (Farmaco f : farmacoService.trovaTuttiFarmaci()) {
				if (f.getNome().equals(nomeFarmaco)) {
					idFarmaco = f.getId();
					nuovoFarmacoPrescritto.setFarmaco(farmacoService.trovaFarmacoDaId(idFarmaco));
				}
			}
			nuovoFarmacoPrescritto.setQuantita(Integer.parseInt(quantitaPrescritta.getText()));

			for (Iterator<FarmacoPrescritto> itr = lista.iterator(); itr.hasNext();) {
				FarmacoPrescritto f = itr.next();

				if (f.getFarmaco().getId() == nuovoFarmacoPrescritto.getFarmaco().getId()
						&& f.getPrescrizione().getId() == nuovoFarmacoPrescritto.getPrescrizione().getId()) {
					nuovoFarmacoPrescritto.setQuantita(nuovoFarmacoPrescritto.getQuantita() + f.getQuantita());
					prescrizioneService.eliminaFarmacoPrescritto(f);
					itr.remove();
					lista.remove(f);

				}
			}

			lista.add(nuovoFarmacoPrescritto);
			prescrizioneService.creaFarmacoPrescritto(nuovoFarmacoPrescritto);

			dispatcher.renderView("prescriviFarmaci", prescrizione);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void terminaPrescrizioneFarmaciAction(ActionEvent event) {
		prescrizione.getFarmaciPrescritti().addAll(lista);
		dispatcher.renderView("aggiungiPrescrizione", prescrizione);
	}

	@FXML
	public void annullaPrescrizioneFarmaciAction(ActionEvent event) {
		try {
			ArrayList<FarmacoPrescritto> listaFarmaci = new ArrayList<>();
			for (FarmacoPrescritto f : lista) {
				listaFarmaci.add(f);
			}
			lista.removeAll(listaFarmaci);
			prescrizioneService.eliminaFarmaciPrescritti(prescrizione);
			dispatcher.renderView("aggiungiPrescrizione", prescrizione);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

}

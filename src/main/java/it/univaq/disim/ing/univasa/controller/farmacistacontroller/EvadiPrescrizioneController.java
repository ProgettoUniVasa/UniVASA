package it.univaq.disim.ing.univasa.controller.farmacistacontroller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.FarmacoService;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.PrescrizioneService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Farmacista;
import it.univaq.disim.ing.univasa.domain.FarmacoPrescritto;
import it.univaq.disim.ing.univasa.domain.Prescrizione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class EvadiPrescrizioneController implements Initializable, DataInitializable<Prescrizione> {

	@FXML
	private TableView<FarmacoPrescritto> farmaciPrescrittiTable;

	@FXML
	private TableColumn<FarmacoPrescritto, String> nomeTableColumn;

	@FXML
	private TableColumn<FarmacoPrescritto, Integer> quantitaTableColumn;

	@FXML
	private TextField numero;

	@FXML
	private DatePicker data;

	@FXML
	private TextField firma;

	@FXML
	private TextField costo;

	@FXML
	private Button evadiButton;

	@FXML
	private Button annullaButton;

	@FXML
	private Label nomePaziente;

	private Prescrizione prescrizione;

	private Farmacista farmacista;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private FarmacoService farmacoService;

	public EvadiPrescrizioneController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
		farmacoService = factory.getFarmacoService();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		nomeTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<FarmacoPrescritto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<FarmacoPrescritto, String> param) {
						return new SimpleStringProperty(param.getValue().getFarmaco().getNome());
					}
				});

		quantitaTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantita"));

	}

	@Override
	public void initializeData(Prescrizione prescrizione) {
		List<FarmacoPrescritto> farmaciPrescritti;
		try {
			this.prescrizione = prescrizione;
			this.numero.setText(prescrizione.getNumero());
			this.data.setValue(prescrizione.getData());
			this.firma.setText(prescrizione.getFirma());

			// Calcolo del costo della prescrizione
			double costoTot = (Math.round(prescrizione.getCostoPrescrizione() * 100) / 100);

			for (FarmacoPrescritto f : prescrizioneService.trovaFarmaciPrescritti(prescrizione)) {
				double costoFarmaco = f.getFarmaco().getCosto() * (f.getQuantita());
				costoFarmaco = (double) (Math.round(costoFarmaco * 100) / 100);
				costoTot += costoFarmaco;
			}

			this.costo.setText("" + costoTot);

			StringBuilder testo = new StringBuilder();
			testo.append(prescrizione.getPaziente().getNome());
			testo.append(" ");
			testo.append(prescrizione.getPaziente().getCognome());
			nomePaziente.setText(testo.toString());

			farmaciPrescritti = prescrizioneService.trovaFarmaciPrescritti(prescrizione);
			ObservableList<FarmacoPrescritto> farmaciData = FXCollections.observableArrayList(farmaciPrescritti);
			farmaciPrescrittiTable.setItems(farmaciData);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public boolean evadiAction(ActionEvent event) {
		try {
			// Controllo sull'evasione della prescrizione
			if (prescrizione.getEvasione().equals("SI")) {
				JOptionPane.showMessageDialog(null,
						" Questa prescrizione è gia stata evasa." + "\n" + "TORNA INDIETRO! ", "ERRORE",
						JOptionPane.ERROR_MESSAGE);
			} else {

				List<FarmacoPrescritto> lista = prescrizioneService.trovaFarmaciPrescritti(prescrizione);
				for (FarmacoPrescritto f : lista) {
					// Controllo sulla quantità richiesta rispetto a quella disponibile
					if (f.getQuantita() > f.getFarmaco().getQuantitaDisponibile()) {

						JOptionPane.showMessageDialog(null,
								" La quantità di farmaci richiesta è maggiore di quella attualmente disponibile." + "\n"
										+ "ANNULLARE L'OPERAZIONE! ",
								"ERRORE", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
				for (FarmacoPrescritto f : lista) {
					f.getFarmaco().setQuantitaDisponibile(f.getFarmaco().getQuantitaDisponibile() - f.getQuantita());
					farmacoService.aggiornaFarmaco(f.getFarmaco());
				}

				prescrizione.setEvasione("SI");
				prescrizioneService.aggiornaPrescrizione(prescrizione);

				dispatcher.renderView("prescrizioniFarmacista", farmacista);
			}

		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return true;
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("prescrizioniFarmacista", farmacista);
	}
}
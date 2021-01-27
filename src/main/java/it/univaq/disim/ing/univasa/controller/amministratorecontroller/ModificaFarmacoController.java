package it.univaq.disim.ing.univasa.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.FarmacoService;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Farmaco;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModificaFarmacoController implements Initializable, DataInitializable<Farmaco> {
	@FXML
	private Label titolo;

	@FXML
	private DatePicker scadenza;

	@FXML
	private TextField costo;

	@FXML
	private TextField quantitaDisponibile;

	@FXML
	private TextField quantitaMinima;

	@FXML
	private Button salvaButton;

	@FXML
	private Button annullaButton;

	private ViewDispatcher dispatcher;

	private FarmacoService farmacoService;

	private Farmaco farmaco;

	private Amministratore amministratore;

	public ModificaFarmacoController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		farmacoService = factory.getFarmacoService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Farmaco farmaco) {
		this.farmaco = farmaco;
		this.scadenza.setValue(farmaco.getScadenza());
		this.costo.setText("" + farmaco.getCosto());
		this.quantitaDisponibile.setText("" + farmaco.getQuantitaDisponibile());
		this.quantitaMinima.setText("" + farmaco.getQuantitaMinima());

		StringBuilder testo = new StringBuilder();
		testo.append("Modifica Farmaco: ");
		testo.append(farmaco.getNome());
		titolo.setText(testo.toString());
	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {

			farmaco.setScadenza(scadenza.getValue());
			farmaco.setCosto(Double.parseDouble(costo.getText()));
			farmaco.setQuantitaDisponibile(Integer.parseInt(quantitaDisponibile.getText()));

			// Controllo sulla quantità minima
			if (Integer.parseInt(quantitaMinima.getText()) != 0) {
				farmaco.setQuantitaMinima(Integer.parseInt(quantitaMinima.getText()));
				farmacoService.aggiornaFarmaco(farmaco);
				dispatcher.renderView("listaFarmaciAmministratore", amministratore);
			} else {
				JOptionPane.showMessageDialog(null, "La quantità minima deve essere di almeno un farmaco", "ATTENZIONE",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void annullaAction(ActionEvent event) {
		dispatcher.renderView("listaFarmaciAmministratore", amministratore);
	}
}
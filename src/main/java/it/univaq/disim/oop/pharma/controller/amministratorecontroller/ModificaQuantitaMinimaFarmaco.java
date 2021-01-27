package it.univaq.disim.oop.pharma.controller.amministratorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.oop.pharma.business.BusinessException;
import it.univaq.disim.oop.pharma.business.FarmacoService;
import it.univaq.disim.oop.pharma.business.MyPharmaBusinessFactory;
import it.univaq.disim.oop.pharma.controller.DataInitializable;
import it.univaq.disim.oop.pharma.domain.Amministratore;
import it.univaq.disim.oop.pharma.domain.Farmaco;
import it.univaq.disim.oop.pharma.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModificaQuantitaMinimaFarmaco implements Initializable, DataInitializable<Farmaco> {

	@FXML
	private TextField nome;

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

	public ModificaQuantitaMinimaFarmaco() {
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
		this.nome.setText(farmaco.getNome());
		nome.setEditable(false);
		this.quantitaDisponibile.setText("" + farmaco.getQuantitaDisponibile());
		this.quantitaMinima.setText("" + farmaco.getQuantitaMinima());

		salvaButton.disableProperty()
				.bind((quantitaDisponibile.textProperty().isEmpty().or(quantitaMinima.textProperty().isEmpty())));
	}

	@FXML
	public void salvaAction(ActionEvent event) {
		try {
			// Controllo sulla quantità minima
			if (Integer.parseInt(quantitaMinima.getText()) != 0) {
				farmaco.setQuantitaDisponibile(Integer.parseInt(quantitaDisponibile.getText()));
				farmaco.setQuantitaMinima(Integer.parseInt(quantitaMinima.getText()));
				farmacoService.aggiornaFarmaco(farmaco);
				dispatcher.renderView("listaFarmaciInEsaurimento", amministratore);
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
		dispatcher.renderView("listaFarmaciInEsaurimento", amministratore);
	}
}

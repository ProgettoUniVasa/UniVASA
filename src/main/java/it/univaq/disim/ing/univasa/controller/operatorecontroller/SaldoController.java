package it.univaq.disim.ing.univasa.controller.operatorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Paziente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class SaldoController implements Initializable, DataInitializable<Paziente> {

	@FXML
	private TextField portafoglio;

	public SaldoController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Paziente paziente) {
		this.portafoglio.setText("" + paziente.getSaldo());
	}

}

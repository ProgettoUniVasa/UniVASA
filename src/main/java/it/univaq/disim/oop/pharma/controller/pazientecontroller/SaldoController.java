package it.univaq.disim.oop.pharma.controller.pazientecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.controller.DataInitializable;
import it.univaq.disim.oop.pharma.domain.Paziente;
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

package it.univaq.disim.oop.pharma.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.pharma.domain.Amministratore;
import it.univaq.disim.oop.pharma.domain.Farmacista;
import it.univaq.disim.oop.pharma.domain.Medico;
import it.univaq.disim.oop.pharma.domain.Paziente;
import it.univaq.disim.oop.pharma.domain.Utente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController implements Initializable, DataInitializable<Utente> {

	@FXML
	private Label benvenutoLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Utente utente) {
		StringBuilder testo = new StringBuilder();
		testo.append("Benvenuto/a ");
		testo.append(utente.getNome());
		testo.append(" ");
		testo.append(utente.getCognome());

		if (utente instanceof Paziente) {
			testo.append("\nAccesso a Pharma come Paziente!");
		}
		if (utente instanceof Medico) {
			testo.append("\nAccesso a Pharma come Medico!");
		}
		if (utente instanceof Farmacista) {
			testo.append("\nAccesso a Pharma come Farmacista!");
		}
		if (utente instanceof Amministratore) {
			testo.append("\nAccesso a Pharma come Amministratore!");
		}
		benvenutoLabel.setText(testo.toString());
	}

}

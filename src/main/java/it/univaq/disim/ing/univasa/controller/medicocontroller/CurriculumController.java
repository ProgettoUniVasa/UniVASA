package it.univaq.disim.ing.univasa.controller.medicocontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Medico;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class CurriculumController implements Initializable, DataInitializable<Medico> {

	@FXML
	private VBox vbox;

	@FXML
	private TextArea curriculumVitaeArea;

	private ViewDispatcher dispatcher;

	public CurriculumController() {
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Medico medico) {
		StringBuilder testo = new StringBuilder();
		testo.append("CURRICULUM VITAE MEDICO ");
		testo.append(medico.getTipologia() + "\n");

		testo.append(medico.getNome());
		testo.append(" ");
		testo.append(medico.getCognome());
		testo.append(" nato il");
		testo.append(" " + medico.getDataNascita());
		testo.append(" a ");
		testo.append(medico.getLuogoNascita());
		testo.append(", residente in ");
		testo.append(medico.getResidenza());
		testo.append(", specializzato in qualità di " + medico.getTipologia());
		testo.append("\ne" + " iscritto all'Albo dei Medici con codice " + medico.getCodiceAlboMedici());
		testo.append(", " + "lavora presso l'ospedale San Salvatore di L'Aquila, Abruzzo. ");
		testo.append("\n\nLingue straniere: \r\n" + "– Inglese livello avanzato; \r\n"
				+ "– Francese livello intermedio; \r\n" + "– Spagnolo livello intermedio.");
		testo.append("\n\nEsperienza lavorativa: \r"
				+ "\n2017-2018 Impiegato come coordinatore medico presso l’Istituto Clinico S. Anna di Brescia. \r\n "
				+ "– Monitoraggio e aggiornamento delle prenotazioni dei pazienti in collaborazione con i medici; \r\n"
				+ "– Coordinamento di appuntamenti e assistenza medica continua ai pazienti; \r\n "
				+ "– Controllo e gestione delle cartelle cliniche dei pazienti; \r\n "
				+ "\n2018-In corso Impiegato come Neurologo presso l'ospedale San Salvatore di L'Aquila. ");
		testo.append("\n\n TELEFONO : " + medico.getTelefono());

		curriculumVitaeArea.setText(testo.toString());
	}

}
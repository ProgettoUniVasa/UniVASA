package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.business.PrenotazioneService;
import it.univaq.disim.ing.univasa.business.UnivasaBusinessFactory;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Prenotazione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

public class VotazioneOnlineController implements Initializable, DataInitializable<Prenotazione> {

	private ViewDispatcher dispatcher;

	private EventoService eventoService;
	private PrenotazioneService prenotazioneService;
	private Prenotazione prenotazione;
	private Elettore elettore;
	private Evento evento;

	@FXML
	TextField regolamento;
	@FXML
	Label sceltaErrorLabel;
	@FXML
	CheckBox candidato1;
	@FXML
	CheckBox candidato2;
	@FXML
	CheckBox candidato3;
	@FXML
	CheckBox candidato4;
	@FXML
	CheckBox candidato5;
	@FXML
	CheckBox candidato6;
	@FXML
	CheckBox candidato7;

	List<CheckBox> checkboxList = new ArrayList<>();
	List<Candidato> candidati = new ArrayList<>();

	public VotazioneOnlineController() {
		dispatcher = ViewDispatcher.getInstance();
		UnivasaBusinessFactory factory = UnivasaBusinessFactory.getInstance();
		eventoService = factory.getEventoService();
		prenotazioneService = factory.getPrenotazioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Prenotazione prenotazione) {
			this.prenotazione = prenotazione;
			this.evento = prenotazione.getEvento();
			this.elettore = prenotazione.getElettore();
			regolamento.setText(evento.getRegolamento());
			regolamento.setEditable(false);
			try {
				inizializzaCheckBox(eventoService.visualizzaCandidati(evento));
			} catch (BusinessException e) {
				e.printStackTrace();
			}
	}

	private void inizializzaCheckBox(List candidatiList) {
		candidati.addAll(candidatiList);
		checkboxList.add(candidato1);
		checkboxList.add(candidato2);
		checkboxList.add(candidato3);
		checkboxList.add(candidato4);
		checkboxList.add(candidato5);
		checkboxList.add(candidato6);
		checkboxList.add(candidato7);
		int numCandidati = candidati.size();
		for (int i=0;i<numCandidati;i++)
			checkboxList.get(i).setText(candidati.get(i).getNome()+" "+candidati.get(i).getCognome());
		for (int i=numCandidati;i<7;i++)
			checkboxList.get(i).setVisible(false);
	}

	@FXML
	public void confermaAction(ActionEvent event) throws BusinessException {
		int count = 0;
		for (int i=0;i<7;i++) {
			if (checkboxList.get(i).isSelected()) count++;
		}
		if(count<=evento.getNumero_preferenze_esprimibili()) {
			for (int i=0;i<7;i++)
				if (checkboxList.get(i).isSelected()) eventoService.aggiungiVoto(candidati.get(i));
			prenotazioneService.vota(evento, elettore);
			dispatcher.renderView("elencoEventiInCorso", elettore);
		}else{
			sceltaErrorLabel.setText("Hai selezionato più di "+evento.getNumero_preferenze_esprimibili()+" preferenze!");
		}
	}

}

package it.univaq.disim.ing.univasa.controller.elettorecontroller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.controller.medicocontroller.Prescrizione;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class PrenotazioneVotazioneInPresenzaElettore implements Initializable, DataInitializable<Evento> {

	@FXML
	private Button prenotatiInPresenzaButton;
	
	@FXML
	private Button prenotatiOnlineButton;
	
	private ViewDispatcher dispatcher;

	private EventoService eventoService;

	private Evento evento;

	private UtenteService utenteService;

	private Elettore elettore;

	public PrenotazioneVotazioneInPresenzaElettore() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		PrescrizioneService = factory.getPrescrizioneService();
		utenteService = factory.getUtenteService();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@Override
	public void initializeData(Evento evento) {
	}
	
	@FXML
	public void prenotatiInPresenzaAction(ActionEvent event) throws BusinessException {
		
	}

	@FXML
	public void annullaPrenotazioneInPresenzaAction(ActionEvent event) throws BusinessException {
		
	}
}

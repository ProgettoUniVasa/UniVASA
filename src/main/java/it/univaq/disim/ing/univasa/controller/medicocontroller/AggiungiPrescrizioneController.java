package it.univaq.disim.ing.univasa.controller.medicocontroller;

import java.awt.HeadlessException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.MyPharmaBusinessFactory;
import it.univaq.disim.ing.univasa.business.UtenteService;
import it.univaq.disim.ing.univasa.controller.DataInitializable;
import it.univaq.disim.ing.univasa.domain.Medico;
import it.univaq.disim.ing.univasa.domain.Paziente;
import it.univaq.disim.ing.univasa.domain.Prescrizione;
import it.univaq.disim.ing.univasa.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AggiungiPrescrizioneController implements Initializable, DataInitializable<Prescrizione> {

	static int countprescrizioni = 0;

	@FXML
	private TextField numero;

	@FXML
	private DatePicker dataPrescrizione;

	@FXML
	private TextField firma;

	@FXML
	private TextField costoPrescrizione;

	@FXML
	private TextField nomePaziente;

	@FXML
	private TextField cognomePaziente;

	@FXML
	private Button salvaPrescrizioneButton;

	@FXML
	private Button prescriviFarmaciButton;

	@FXML
	private Button annullaPrescrizioneButton;

	private ViewDispatcher dispatcher;

	private PrescrizioneService prescrizioneService;

	private Prescrizione prescrizione;

	private UtenteService utenteService;

	private Medico medico;

	public AggiungiPrescrizioneController() {
		dispatcher = ViewDispatcher.getInstance();
		MyPharmaBusinessFactory factory = MyPharmaBusinessFactory.getInstance();
		prescrizioneService = factory.getPrescrizioneService();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Prescrizione prescrizione) {
		this.prescrizione = prescrizione;
		this.medico = prescrizione.getMedico();

		try {
			prescrizioneService.trovaPrescrizioniMedico(medico);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		this.numero.setText(prescrizione.getNumero());
		this.dataPrescrizione.setValue(prescrizione.getData());
		this.firma.setText(prescrizione.getFirma());
		this.costoPrescrizione.setText("" + prescrizione.getCostoPrescrizione());
		this.nomePaziente.setText("" + prescrizione.getPaziente().getNome());
		this.cognomePaziente.setText("" + prescrizione.getPaziente().getCognome());

		// Si disabilita il bottone se i campi di seguito non rispettano le proprietà
		// definite
		salvaPrescrizioneButton.disableProperty().bind((numero.textProperty().isEmpty()
				.or(nomePaziente.textProperty().isEmpty().or(cognomePaziente.textProperty().isEmpty()))));

	}

	@FXML
	public void salvaPrescrizioneAction(ActionEvent event) {
		try {
			int countpazienti = 0;

			prescrizione.setData(dataPrescrizione.getValue());
			prescrizione.setFirma(firma.getText());
			prescrizione.setCostoPrescrizione(Double.parseDouble(costoPrescrizione.getText()));

			String nomePazienteVisitato = nomePaziente.getText();
			String cognomePazienteVisitato = cognomePaziente.getText();
			Integer idPaziente;

			for (Paziente p : utenteService.trovaTuttiPazienti()) {

				if (p.getNome().equals(nomePazienteVisitato) && p.getCognome().equals(cognomePazienteVisitato)) {

					idPaziente = p.getId();
					prescrizione.setPaziente((Paziente) utenteService.trovaUtenteDaId(idPaziente));
					countpazienti++;

				}
			}

			if ((!numeroDuplicato()) && (!prescrizione.getFarmaciPrescritti().isEmpty())) {

				prescrizione.setNumero(numero.getText());
				prescrizioneService.aggiornaPrescrizione(prescrizione);
				medico.getPrescrizioni().add(prescrizione);
				dispatcher.renderView("prescrizioniMedico", prescrizione.getMedico());

			} else {
				if (countpazienti == 0) {

					JOptionPane.showMessageDialog(null, "Non c'è nessun paziente registrato con questi dati",
							"ATTENZIONE", JOptionPane.WARNING_MESSAGE);

				}

				if (prescrizione.getFarmaciPrescritti().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Non hai prescritto nessun Farmaco", "Errore",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	// Metodo che restituisce true se il numero di una prescrizione è già presente
	// tra i numeri delle prescrizioni già esistenti
	private boolean numeroDuplicato() {
		boolean cond1 = false;
		int i;
		try {
			for (i = 0; i < prescrizioneService.numeriPrescrizioni().size() - 1; i++) {

				if (numero.getText().toString().equals(prescrizioneService.numeriPrescrizioni().get(i))) {

					JOptionPane.showMessageDialog(null, "Esiste già una prescrizione con questo numero", "ATTENZIONE",
							JOptionPane.WARNING_MESSAGE);
					cond1 = true;
					break;

				}

			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
		return cond1;
	}

	@FXML
	public void annullaPrescrizioneAction(ActionEvent event) {

		try {
			if (prescrizione.getId() != null) {

				prescrizioneService.eliminaPrescrizione(prescrizione);

				if (!(prescrizione.getFarmaciPrescritti().isEmpty()))

					prescrizioneService.eliminaFarmaciPrescritti(prescrizione);

			}

			dispatcher.renderView("prescrizioniMedico", prescrizione.getMedico());
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void prescriviFarmaciAction(ActionEvent event) throws BusinessException {

		prescrizione.setNumero(numero.getText());
		prescrizione.setData(dataPrescrizione.getValue());
		prescrizione.setFirma(firma.getText());
		prescrizione.setCostoPrescrizione(Double.parseDouble(costoPrescrizione.getText()));

		int count = 0;
		String nomePazienteVisitato = nomePaziente.getText();
		String cognomePazienteVisitato = cognomePaziente.getText();
		Integer idPaziente;

		// La variabile count viene incrementata se il paziente con il nome e cognome
		// inserito è presente nella lista dei pazienti registrati. Alla fine del ciclo
		// deve esserci esattamente un paziente con tali caratteristiche
		for (Paziente p : utenteService.trovaTuttiPazienti()) {
			if (p.getNome().equals(nomePazienteVisitato) && p.getCognome().equals(cognomePazienteVisitato)) {

				idPaziente = p.getId();
				prescrizione.setPaziente((Paziente) utenteService.trovaUtenteDaId(idPaziente));
				count++;
				if (prescrizione.getId() == null) {
					prescrizioneService.creaPrescrizione(prescrizione);

				}
				dispatcher.renderView("prescriviFarmaci", prescrizione);
				break;
			}

		}
		if (count != 1)
			JOptionPane.showMessageDialog(null, "Non c'è nessun paziente registrato con questi dati", "ATTENZIONE",
					JOptionPane.WARNING_MESSAGE);
	}
}
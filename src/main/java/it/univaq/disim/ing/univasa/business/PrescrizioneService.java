package it.univaq.disim.ing.univasa.business;

import java.util.List;

import it.univaq.disim.ing.univasa.domain.FarmacoPrescritto;
import it.univaq.disim.ing.univasa.domain.Medico;
import it.univaq.disim.ing.univasa.domain.Paziente;
import it.univaq.disim.ing.univasa.domain.Prescrizione;

public interface PrescrizioneService {

	Prescrizione trovaPrescrizioneDaId(int id) throws BusinessException;

	FarmacoPrescritto trovaFarmacoPrescrittoDaId(int id) throws BusinessException;

	List<Prescrizione> trovaTuttePrescrizioni() throws BusinessException;

	// Metodo che restituisce la lista delle prescrizioni di un medico
	List<Prescrizione> trovaPrescrizioniMedico(Medico medico) throws BusinessException;

	// Metodo che restituisce la lista delle prescrizioni di un paziente
	List<Prescrizione> trovaPrescrizioniPaziente(Paziente paziente) throws BusinessException;

	// Metodo che restituisce la lista dei farmaci prescritti di una prescrizione
	List<FarmacoPrescritto> trovaFarmaciPrescritti(Prescrizione prescrizione) throws BusinessException;

	void creaPrescrizione(Prescrizione prescrizione) throws BusinessException;

	void creaFarmacoPrescritto(FarmacoPrescritto nuovoFarmacoPrescritto) throws BusinessException;

	void aggiornaPrescrizione(Prescrizione prescrizione) throws BusinessException;

	void eliminaPrescrizione(Prescrizione prescrizione) throws BusinessException;

	// Metodo che elimina i farmaci prescritti da una prescrizione
	void eliminaFarmaciPrescritti(Prescrizione prescrizione) throws BusinessException;

	// Metodo che elimina un farmaco prescritto
	void eliminaFarmacoPrescritto(FarmacoPrescritto farmacoPrescritto) throws BusinessException;

	// Metodo che restituisce una lista con il numero di tutte le prescrizioni
	List<String> numeriPrescrizioni() throws BusinessException;

}

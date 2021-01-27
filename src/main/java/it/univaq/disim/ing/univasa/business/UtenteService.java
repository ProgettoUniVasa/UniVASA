package it.univaq.disim.ing.univasa.business;

import java.util.List;

import it.univaq.disim.ing.univasa.domain.Farmacista;
import it.univaq.disim.ing.univasa.domain.Medico;
import it.univaq.disim.ing.univasa.domain.Paziente;
import it.univaq.disim.ing.univasa.domain.Utente;

public interface UtenteService {

	// Metodo che permette ad un utente di effettuare il login
	Utente autenticazione(String username, String password) throws UtenteNotFoundException, BusinessException;

	Utente trovaUtenteDaId(int id) throws BusinessException;

	List<Utente> trovaTuttiUtenti() throws BusinessException;

	// Metodo che restituisce una lista di tutti i farmacisti
	List<Farmacista> trovaTuttiFarmacisti() throws BusinessException;

	// Metodo che restituisce una lista di tutti i pazienti
	List<Paziente> trovaTuttiPazienti() throws BusinessException;

	// Metodo che restituisce una lista di tutti i medici
	List<Medico> trovaTuttiMedici() throws BusinessException;

	void creaFarmacista(Farmacista farmacista) throws BusinessException;

	void creaPaziente(Paziente paziente) throws BusinessException;

	void creaMedico(Medico medico) throws BusinessException;

	void aggiornaFarmacista(Farmacista farmacista) throws BusinessException;

	void aggiornaPaziente(Paziente paziente) throws BusinessException;

	void aggiornaMedico(Medico medico) throws BusinessException;

	void eliminaUtente(Utente utente) throws UtenteNotFoundException, BusinessException;
}
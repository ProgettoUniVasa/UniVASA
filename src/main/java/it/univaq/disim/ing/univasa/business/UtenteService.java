package it.univaq.disim.ing.univasa.business;

import java.util.List;

import it.univaq.disim.ing.univasa.domain.Amministratore;
import it.univaq.disim.ing.univasa.domain.Candidato;
import it.univaq.disim.ing.univasa.domain.Elettore;
import it.univaq.disim.ing.univasa.domain.Evento;
import it.univaq.disim.ing.univasa.domain.Operatore;
import it.univaq.disim.ing.univasa.domain.Utente;

public interface UtenteService {

	// Metodo che permette ad un utente di effettuare il login
	Utente autenticazione(String username, String password) throws UtenteNotFoundException, BusinessException;

	Utente trovaUtenteDaId(Long id) throws BusinessException;

	List<Utente> trovaTuttiUtenti() throws BusinessException;

	// Metodo che restituisce una lista di tutti gli amministratori
	List<Amministratore> trovaTuttiAmministratori() throws BusinessException;

	// Metodo che restituisce una lista di tutti gli operatori
	List<Operatore> trovaTuttiOperatori() throws BusinessException;

	// Metodo che restituisce una lista di tutti gli elettori
	List<Elettore> trovaTuttiElettori() throws BusinessException;

	void creaAmministratore(Amministratore amministratore) throws BusinessException;

	void creaOperatore(Operatore operatore) throws BusinessException;

	void creaElettore(Elettore elettore) throws BusinessException;

	void creaCandidato(Candidato candidato, Evento evento) throws BusinessException;

	List<Candidato> trovaTuttiCandidati(Evento evento) throws BusinessException;

	// Amministratore
	void accettaCertificato(String certificato) throws BusinessException;

	void rifiutaCertificato(String certificato) throws BusinessException;

	List<Elettore> gestionePrenotazioni(Evento evento) throws BusinessException;

	void modificaOperatore(Operatore operatore) throws BusinessException;

	void modificaAmministratore(Amministratore amministratore) throws BusinessException;

	void eliminaUtente(Utente utente) throws UtenteNotFoundException, BusinessException;

	Utente utenteDaEmail(String email) throws UtenteNotFoundException, BusinessException;

	void eliminaCandidato(Candidato candidato) throws BusinessException;

	List<String> trovaEmailTuttiOperatori() throws BusinessException;
}
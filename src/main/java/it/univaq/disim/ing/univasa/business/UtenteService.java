package it.univaq.disim.ing.univasa.business;

import java.util.List;

import it.univaq.disim.ing.univasa.domain.*;

import javax.swing.text.Element;

public interface UtenteService {

	// Metodo che permette ad un utente di effettuare il login
	Utente autenticazione(String username, String password) throws UtenteNotFoundException, BusinessException;

	Utente trovaUtenteDaId(int id) throws BusinessException;

	List<Utente> trovaTuttiUtenti() throws BusinessException;

	// Metodo che restituisce una lista di tutti gli amministratorei
	List<Amministratore> trovaTuttiAmministratori() throws BusinessException;

	// Metodo che restituisce una lista di tutti gli operatori
	List<Operatore> trovaTuttiOperatori() throws BusinessException;

	// Metodo che restituisce una lista di tutti gli elettori
	List<Elettore> trovaTuttiElettori() throws BusinessException;

	void creaAmministratore(Amministratore amministratore) throws BusinessException;

	void creaOperatore(Operatore operatore) throws BusinessException;

	void creaElettore(Elettore elettore) throws BusinessException;

	void creaElettoreInSede(ElettoreInSede elettoreInSede) throws BusinessException;

	void creaElettoreOnline(ElettoreOnline elettoreOnline) throws BusinessException;

	void creaCandidato(Candidato candidato) throws BusinessException;

	// Amministratore
	boolean creaEvento(Evento evento) throws  BusinessException;
	boolean eliminaEvento(Evento evento) throws  BusinessException;
	boolean creaReport(String report) throws  BusinessException;
	boolean verificaCertificato(String certificato) throws  BusinessException;
	List<Elettore> gestionePrenotazioni(Evento evento) throws  BusinessException;
	boolean creaTurnazione(Turnazione turnazione) throws  BusinessException;
	boolean associaTurnazione(Turnazione turnazione, Operatore operatore) throws  BusinessException;
	List<Utente> visualizzaUtenti() throws  BusinessException;
	List<Candidato> visualizzaCandidati(Evento evento) throws  BusinessException;

	// Operatore
	List<Evento> visualizzaEventi(Operatore operatore) throws BusinessException;					// da aggiungere al CD
	boolean riconoscimentoElettore(ElettoreInSede elettoreInSede) throws  BusinessException;		// ma questa viene fatta con un documneto????
	boolean calcoloRisulatti(Evento evento) throws  BusinessException;
	boolean caricaRisultati(Evento evento) throws  BusinessException;
	List<Turnazione> visualizzaturnazioni(Operatore operatore) throws  BusinessException;
	List<ElettoreInSede> visualizzaPrenotatiInSede(Evento evento) throws  BusinessException;

	// Elettore generico -- ma dove lo salva??? Non c'è un DB
	void prenotazioneInSede(Elettore elettore, Evento evento) throws BusinessException;
	void prenotazioneOnline(Elettore elettore, Evento evento) throws BusinessException;

	// ElettoreOnline
	void vota(ElettoreOnline elettoreOnline, Evento evento) throws BusinessException;
	boolean riconoscimentoOnline(ElettoreOnline elettoreOnline, Evento evento) throws BusinessException;

	// SchedaElettorale
	List<ElettoreOnline> visualizzaPrenotatiOnline(SchedaElettorale schedaElettorale) throws BusinessException;

	void eliminaUtente(Utente utente) throws UtenteNotFoundException, BusinessException;
}
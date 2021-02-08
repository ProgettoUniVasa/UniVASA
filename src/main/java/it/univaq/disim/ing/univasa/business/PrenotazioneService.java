package it.univaq.disim.ing.univasa.business;

import it.univaq.disim.ing.univasa.domain.*;

import java.util.List;

public interface PrenotazioneService {

	void prenotazioneInSede(Elettore elettore, Evento evento) throws BusinessException;
	void prenotazioneOnline(Elettore elettore, Evento evento) throws BusinessException;

	List<Prenotazione> trovaPrenotazioniElettore(Elettore elettore) throws BusinessException;
	Prenotazione trovaPrenotazioneDaId(Long id) throws  BusinessException;
	List<Prenotazione> trovaPrenotazioniOnlineInCorso(Elettore elettore) throws BusinessException;

    void eliminaPrenotazione(Prenotazione prenotazione) throws BusinessException;

	void cambioModalita(Prenotazione prenotazione) throws BusinessException;
}